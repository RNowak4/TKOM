package parser;

import lexer.Lexer;
import parser.structures.*;
import utils.Token;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Parser.class);
    private Lexer lexer;
    private Token bufferedToken = null;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        return Stream.of(tokenTypes).anyMatch(t -> t.equals(token.getTokenType()));
    }

    public Program parse() {
        return null;
    }

    /**
     * Check if token is accepted. Clears bufferedToken
     *
     * @return next token
     */
    public Token accept(final TokenType... tokenTypes) {
        Token nextToken;
        if (bufferedToken != null) {
            nextToken = bufferedToken;
            bufferedToken = null;
        } else
            nextToken = lexer.nextToken();

        if (isAcceptable(nextToken, tokenTypes)) {
            return nextToken;
        } else {
            throwUnexpectedToken(nextToken, tokenTypes);
        }

        return bufferedToken;
    }

    /**
     * Same as accept, but doesn't clear bufferedToken
     *
     * @return next token
     */
    public Token checkAccept(TokenType... tokenTypes) {
        if (bufferedToken == null)
            bufferedToken = lexer.nextToken();

        if (isAcceptable(bufferedToken, tokenTypes)) {
            return bufferedToken;
        } else {
            throwUnexpectedToken(bufferedToken, tokenTypes);
        }

        return bufferedToken;
    }

    private void throwUnexpectedToken(final Token token, final TokenType... tokenTypes) {
        final StringBuilder sb = new StringBuilder()
                .append("Unexpected token: ")
                .append(token.getTokenString())
                .append(" of token type: ")
                .append(token.getTokenType())
                .append(" at position: [")
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

    /**
     * @return bufferedToken
     */
    public Token accept() {
        if (bufferedToken == null)
            throw new RuntimeException("Trying to get buffered token while bufferedToken == null");

        Token tmp = bufferedToken;
        bufferedToken = null;
        return tmp;
    }

    /**
     * Same as accept(), but doesn't clear bufferedToken
     *
     * @return next token
     */
    public Token getNextToken() {
        if (bufferedToken != null)
            return bufferedToken;

        return bufferedToken = lexer.nextToken();
    }

    public boolean checkNextTokenType(final TokenType... tokenTypes) {
        return Stream.of(tokenTypes).anyMatch(t -> t == getNextToken().getTokenType());
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

    public Node parseFunctionCallOrVariable() {
        final String name = accept(TokenType.ID).getTokenString();
        if (checkNextTokenType(TokenType.PARENTH_OPEN)) {
            final FunctionCall functionCall = new FunctionCall();
            final List<Node> arguments = parseParameters().stream()
                    .map(Variable::new).collect(Collectors.toList());
            functionCall.setName(name);
            functionCall.setArguments(arguments);

            return functionCall;
        } else if (checkNextTokenType(TokenType.ASSIGN)) {
            final AssignStatement assignStatement = new AssignStatement();
            assignStatement.setVariable(new Variable(name));
            accept();
            assignStatement.setValue(parseAssignable());
            return assignStatement;
        } else
            return new Variable(name);
    }

    public Node parseAssignable() {
        if (checkNextTokenType(TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD)) {
            final Expression expression = new Expression();
            expression.parse(this);
            return expression;
        } else
            return parseFunctionCallOrVariable();
    }
}