package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Expression extends Parsable implements Executable {
    private MultiplicativeExpression leftExpression = new MultiplicativeExpression();
    private MultiplicativeExpression rightExpression;
    private TokenType operator = TokenType.UNDEFINED;

    public MultiplicativeExpression getLeftExpression() {
        return leftExpression;
    }

    public MultiplicativeExpression getRightExpression() {
        return rightExpression;
    }

    public TokenType getOperator() {
        return operator;
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
            rightExpression = new MultiplicativeExpression();
            rightExpression.parse(parser);
        }
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
