package parser;

import lexer.Lexer;
import utils.Token;
import utils.TokenType;
import utils.structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserImpl implements Parser {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ParserImpl.class);
    private Lexer lexer;
    private Token bufferedToken = null;

    public ParserImpl(Lexer lexer) {
        this.lexer = lexer;
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        return Stream.of(tokenTypes).anyMatch(t -> t.equals(token.getTokenType()));
    }

    @Override
    public Program parse() {
        throw new UnsupportedOperationException("TODO");
    }

    private Token accept(final TokenType... tokenTypes) {
        Token nextToken;
        if (bufferedToken != null) {
            nextToken = bufferedToken;
            bufferedToken = null;
        } else
            nextToken = lexer.nextToken();

        if (isAcceptable(nextToken, tokenTypes)) {
            return nextToken;
        } else {
            throwUnexceptedToken(nextToken, tokenTypes);
        }

        return bufferedToken;
    }

    private Token checkAccept(TokenType... tokenTypes) {
        if (bufferedToken == null)
            bufferedToken = lexer.nextToken();

        if (isAcceptable(bufferedToken, tokenTypes)) {
            return bufferedToken;
        } else {
            throwUnexceptedToken(bufferedToken, tokenTypes);
        }

        return bufferedToken;
    }

    private void throwUnexceptedToken(final Token token, final TokenType... tokenTypes) {
        final StringBuilder sb = new StringBuilder()
                .append("Unexptected token: ")
                .append(token.getTokenString())
                .append(" of token type: ")
                .append(token.getTokenType())
                .append(" in position: [")
                .append(lexer.getLine())
                .append(", ")
                .append(lexer.getCharPosition())
                .append("]. ")
                .append("Expected one of the following: ");

        for (TokenType tokenType : tokenTypes) {
            sb
                    .append(tokenType)
                    .append(" ");
        }

        throw new RuntimeException(sb.toString());
    }

    private Token accept() {
        if (bufferedToken == null)
            throw new RuntimeException("Trying to get buffered token while bufferedToken == null");

        Token tmp = bufferedToken;
        bufferedToken = null;
        return tmp;
    }

    private Token getNextToken() {
        if (bufferedToken != null)
            return bufferedToken;

        return bufferedToken = lexer.nextToken();
    }

    private boolean checkNextTokenType(final TokenType... tokenTypes) {
        return Stream.of(tokenTypes).anyMatch(t -> t == getNextToken().getTokenType());
    }

    public Function parseFunction() {
        log.trace("Started parsing function.");
        final Function function = new Function();
        if (accept(TokenType.FUNCTION, TokenType.END).getTokenType() == TokenType.END)
            return null;

        function.setName(accept(TokenType.ID).getTokenString());
        function.setParameters(parseParameters());
        function.setStatementBlock(parseStatementBlock());
        log.trace("Successfully parsed function " + function);

        return function;
    }

    public StatementBlock parseStatementBlock() {
        log.trace("Parsing statement block.");
        final StatementBlock statementBlock = new StatementBlock();

        accept(TokenType.BRACKET_OPEN);
        Token token;
        while ((token = checkAccept(TokenType.IF, TokenType.WHILE, TokenType.DEF, TokenType.ID, TokenType.RETURN)).getTokenType()
                != TokenType.BRACKET_CLOSE) {

            switch (token.getTokenType()) {
                case IF:
                    statementBlock.addInstruction(parseIfStatement());
                    break;

                case WHILE:
                    statementBlock.addInstruction(parseWhileStatement());
                    break;

                case DEF:
                    statementBlock.addInstruction(parseInitStatement());
                    break;

                case ID:
                    statementBlock.addInstruction(parseFunctionCallOrVariable());
                    break;

                case RETURN:
                    statementBlock.addInstruction(parseReturnStatement());
                    break;

                default:
                    throw new RuntimeException("You shouldn't see that message. Sth is broken.");
            }
        }
        log.trace("Successfully parsed statement block.");

        return statementBlock;
    }

    public Node parseIfStatement() {
        final IfStatement ifStatement = new IfStatement();

        accept(TokenType.PARENTH_OPEN);
        ifStatement.setCondition(parseCondition());
        accept(TokenType.PARENTH_CLOSE);
        ifStatement.setTrueBlock(parseStatementBlock());
        if (getNextToken().getTokenType() == TokenType.ELSE)
            ifStatement.setElseBlock(parseStatementBlock());

        return ifStatement;
    }

    public Condition parseCondition() {
        final Condition condition = new Condition();

        condition.addOperand(parseAndCondition());
        if (checkNextTokenType(TokenType.OR)) {
            condition.setOperator(TokenType.OR);
            condition.addOperand(parseAndCondition());
        }

        return condition;
    }

    public Literal parseLiteral() {
        final Literal literal = new Literal();

        boolean minused = false;
        if (checkNextTokenType(TokenType.SUB)) {
            minused = true;
            accept();
        } else if (checkNextTokenType(TokenType.ADD))
            accept();

        if (checkNextTokenType(TokenType.RE_NUMBER)) {
            literal.setReValue(parseNumberValue(minused));

            minused = false;
            if (checkNextTokenType(TokenType.SUB, TokenType.ADD)) {
                if (checkNextTokenType(TokenType.SUB))
                    minused = true;
                accept();
                if (!checkNextTokenType(TokenType.IM_NUMBER))
                    // to show error
                    accept(TokenType.IM_NUMBER);
            }
            if (checkNextTokenType(TokenType.IM_NUMBER))
                literal.setImValue(parseNumberValue(minused));
        } else if (checkNextTokenType(TokenType.IM_NUMBER)) {
            if (checkNextTokenType(TokenType.IM_NUMBER))
                literal.setImValue(parseNumberValue(minused));
        }

        return literal;
    }

    private int parseNumberValue(boolean minused) {
        final Token numberToken = accept(TokenType.RE_NUMBER, TokenType.IM_NUMBER);
        int number = getImValue(numberToken.getTokenString());
        if (minused)
            return -number;
        else
            return number;
    }

    private int getImValue(final String number) {
        if (number.length() == 1)
            return 1;

        return Integer.valueOf(number.replace("j", ""));
    }

    public Variable parseVariable() {
        final Variable variable = new Variable();

        final Token varToken = accept(TokenType.ID);
        variable.setName(varToken.getTokenString());

        return variable;
    }

    public List<String> parseParameters() {
        log.trace("Started parsing parameters.");
        final List<String> parameters = new ArrayList<>();

        accept(TokenType.PARENTH_OPEN);
        Token token = accept(TokenType.PARENTH_CLOSE, TokenType.ID);
        if (token.getTokenType() != TokenType.PARENTH_CLOSE) {
            parameters.add(token.getTokenString());
            while (accept(TokenType.COMMA, TokenType.PARENTH_CLOSE).getTokenType() != TokenType.PARENTH_CLOSE) {
                token = accept(TokenType.ID);
                parameters.add(token.getTokenString());
            }
        }
        log.trace("Successfully parsed " + parameters.size() + " parameters.");

        return parameters;
    }

    public Condition parsePrimaryCondition() {
        final Condition condition = new Condition();

        if (checkNextTokenType(TokenType.UNARY)) {
            condition.setNegated(true);
            accept();
        }
        Token token = checkAccept(TokenType.PARENTH_OPEN, TokenType.ID, TokenType.ADD, TokenType.SUB, TokenType.RE_NUMBER, TokenType.IM_NUMBER);
        switch (token.getTokenType()) {
            case PARENTH_OPEN: {
                accept();
                condition.addOperand(parseCondition());
                accept(TokenType.PARENTH_CLOSE);
            }
            break;

            case ID: {
                condition.addOperand(parseVariable());
            }
            break;

            case ADD:
            case SUB:
            case RE_NUMBER:
            case IM_NUMBER: {
                condition.addOperand(parseLiteral());
            }
            break;

            default:
                throw new RuntimeException("You shouldn't see that message. Sth is broken.");
        }

        return condition;
    }

    public Condition parseRelationalCondition() {
        final Condition condition = new Condition();

        condition.addOperand(parsePrimaryCondition());
        if (checkNextTokenType(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER, TokenType.GREATER_EQUALS)) {
            final Token operator = accept();
            condition.setOperator(operator.getTokenType());
            condition.addOperand(parsePrimaryCondition());
        }

        return condition;
    }

    public Condition parseEqualityCondition() {
        final Condition condition = new Condition();

        condition.addOperand(parsePrimaryCondition());
        if (checkNextTokenType(TokenType.EQUALS, TokenType.NOT_EQUALS)) {
            final Token operator = accept();
            if (operator.getTokenType() == TokenType.NOT_EQUALS)
                condition.setNegated(true);
            condition.setOperator(operator.getTokenType());
            condition.addOperand(parsePrimaryCondition());
        }

        return condition;
    }

    public Condition parseAndCondition() {
        final Condition condition = new Condition();

        condition.addOperand(parseEqualityCondition());
        if (checkNextTokenType(TokenType.AND)) {
            condition.setOperator(TokenType.AND);
            accept();
            condition.addOperand(parseEqualityCondition());
        }

        return condition;
    }

    public Expression parseExpression() {
        final Expression expression = new Expression();

        expression.addOperand(parseMultiplicativeExpression());
        if (checkNextTokenType(TokenType.ADD, TokenType.SUB)) {
            final Token token = accept();
            expression.addOperator(token.getTokenType());
            expression.addOperand(parseMultiplicativeExpression());
        }

        return expression;
    }

    public Expression parsePrimaryExpression() {
        final Expression expression = new Expression();

        final Token token = checkAccept(TokenType.ID, TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD,
                TokenType.PARENTH_OPEN);
        switch (token.getTokenType()) {
            case ID: {
                expression.addOperand(parseVariable());
            }
            break;

            case RE_NUMBER:
            case IM_NUMBER:
            case SUB:
            case ADD: {
                expression.addOperand(parseLiteral());
            }
            break;

            case PARENTH_OPEN: {
                accept();
                expression.addOperand(parseExpression());
                accept(TokenType.PARENTH_CLOSE);
            }
            break;

            default:
                throw new RuntimeException("You shouldn't see that message. Sth is broken.");
        }

        return expression;
    }

    public Expression parseMultiplicativeExpression() {
        final Expression expression = new Expression();

        expression.addOperand(parsePrimaryExpression());
        if (checkNextTokenType(TokenType.MUL, TokenType.DIV, TokenType.MODULO)) {
            final Token token = accept();
            expression.addOperator(token.getTokenType());
            expression.addOperand(parsePrimaryExpression());
        }

        return expression;
    }

    public WhileStatement parseWhileStatement() {
        final WhileStatement whileStatement = new WhileStatement();

        accept(TokenType.PARENTH_OPEN);
        whileStatement.setCondition(parseCondition());
        accept(TokenType.PARENTH_CLOSE);
        whileStatement.setStatementBlock(parseStatementBlock());

        return whileStatement;
    }

    public ReturnStatement parseReturnStatement() {
        final ReturnStatement returnStatement = new ReturnStatement();

        accept(TokenType.RETURN);

        if (checkNextTokenType(TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD))
            returnStatement.setReturnVal(parseLiteral());
        else
            returnStatement.setReturnVal(parseFunctionCallOrVariable());

        accept(TokenType.SEMICOLON);
        return returnStatement;
    }

    public InitStatement parseInitStatement() {
        final InitStatement initStatement = new InitStatement();

        accept(TokenType.DEF);
        initStatement.setVariable(parseVariable());
        if (checkNextTokenType(TokenType.ASSIGN)) {
            accept();
            initStatement.setAssignment(parseAssignable());
        }

        return initStatement;
    }

    private Node parseAssignable() {
        if (checkNextTokenType(TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD))
            return parseExpression();
        else
            return parseFunctionCallOrVariable();
    }

    private Node parseFunctionCallOrVariable() {
        final FunctionCall functionCall = new FunctionCall();

        final String name = accept(TokenType.ID).getTokenString();
        if (checkNextTokenType(TokenType.PARENTH_OPEN)) {
            final List<Node> arguments = parseParameters().stream()
                    .map(Variable::new).collect(Collectors.toList());
            functionCall.setName(name);
            functionCall.setArguments(arguments);

            return functionCall;
        } else
            return new Variable(name);
    }

}