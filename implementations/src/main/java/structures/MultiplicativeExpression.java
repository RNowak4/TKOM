package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class MultiplicativeExpression extends ParseTree {
    private ParseTree leftParseTree = new PrimaryExpression();
    private ParseTree rightParseTree;
    private TokenType operator = TokenType.UNDEFINED;

    public ParseTree getLeftParseTree() {
        return leftParseTree;
    }

    public ParseTree getRightParseTree() {
        return rightParseTree;
    }

    public void setLeftParseTree(ParseTree parseTree) {
        this.leftParseTree = parseTree;
    }

    @Override
    public TokenType getOperator() {
        return operator;
    }

    @Override
    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public void setRightParseTree(ParseTree parseTree) {
        this.rightParseTree = parseTree;
    }

    @Override
    public Type getType() {
        return Type.MultiplicativeExpression;
    }

    @Override
    public void parse(final Parser parser) {
        leftParseTree.parse(parser);
        if (parser.checkNextTokenType(TokenType.MUL, TokenType.DIV, TokenType.MODULO)) {
            final Token token = parser.accept();
            operator = token.getTokenType();
            rightParseTree = new MultiplicativeExpression();
            rightParseTree.parse(parser);
        }
        normalize(this);
    }

    public Literal execute(Executor executor, Scope scope) {
        final Literal leftExpressionResult = leftParseTree.execute(executor, scope);

        if (rightParseTree == null) {
            return leftExpressionResult;
        } else {
            final Literal rightExpressionResult = rightParseTree.execute(executor, scope);

            if (operator == TokenType.MUL)
                return Literal.mul(leftExpressionResult, rightExpressionResult);
            else if (operator == TokenType.DIV)
                return Literal.div(leftExpressionResult, rightExpressionResult);
            else if (operator == TokenType.MODULO)
                return Literal.mod(leftExpressionResult, rightExpressionResult);
            else
                throw new RuntimeException("Bad operator!");
        }
    }
}
