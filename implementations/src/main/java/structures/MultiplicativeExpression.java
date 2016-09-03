package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class MultiplicativeExpression extends Expression {

    public MultiplicativeExpression() {
        this.leftExpression = new PrimaryExpression();
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
            rightExpression = new MultiplicativeExpression();
            rightExpression.parse(parser);
        }
        normalize(this);
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
