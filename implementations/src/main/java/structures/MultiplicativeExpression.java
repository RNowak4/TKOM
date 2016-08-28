package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class MultiplicativeExpression extends Parsable {
    private PrimaryExpression leftExpression = new PrimaryExpression();
    private PrimaryExpression rightExpression;
    private TokenType operator = TokenType.UNDEFINED;

    public PrimaryExpression getLeftExpression() {
        return leftExpression;
    }

    public PrimaryExpression getRightExpression() {
        return rightExpression;
    }

    public TokenType getOperator() {
        return operator;
    }

    @Override
    public Type getType() {
        return Type.MultiplicativeExpression;
    }

    @Override
    public void parse(final Parser parser) {
        leftExpression.parse(parser);
        if (parser.checkNextTokenType(TokenType.MUL, TokenType.DIV, TokenType.MODULO)) {
            final Token token = parser.accept();
            operator = token.getTokenType();
            rightExpression = new PrimaryExpression();
            rightExpression.parse(parser);
        }
    }

    public Literal execute(Executor executor, Scope scope) {
        final Literal leftExpressionResult = leftExpression.execute(executor, scope);

        if (rightExpression == null) {
            return leftExpressionResult;
        } else {
            final Literal rightExpressionResult = rightExpression.execute(executor, scope);

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
