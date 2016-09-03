package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class AdditiveExpression extends Expression {

    public AdditiveExpression() {
        this.leftExpression = new MultiplicativeExpression();
    }

    @Override
    public Type getType() {
        return Type.Expression;
    }

    @Override
    public void parse(final Parser parser) {
        leftExpression.parse(parser);
        if (parser.checkNextTokenType(TokenType.ADD, TokenType.SUB)) {
            final Token token = parser.accept();
            operator = token.getTokenType();
            rightExpression = new AdditiveExpression();
            rightExpression.parse(parser);
        }
        normalize(this);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal leftExpressionResult = leftExpression.execute(executor, scope);

        if (rightExpression == null) {
            return leftExpressionResult;
        } else {
            final Literal rightExpressionResult = rightExpression.execute(executor, scope);

            if (operator == TokenType.ADD)
                return Literal.add(leftExpressionResult, rightExpressionResult);
            else if (operator == TokenType.SUB)
                return Literal.sub(leftExpressionResult, rightExpressionResult);
            else
                throw new RuntimeException("Bad operator!");
        }
    }
}
