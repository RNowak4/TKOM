package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class EqualityCondition extends Condition {
    private boolean negated = false;

    public EqualityCondition() {
        this.leftCondition = new RelationalCondition();
    }

    @Override
    public Type getType() {
        return Type.EqualityCondition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.EQUALS, TokenType.NOT_EQUALS)) {
            final Token operator = parser.accept();
            if (operator.getTokenType() == TokenType.NOT_EQUALS)
                negated = true;
            rightCondition = new EqualityCondition();
            rightCondition.parse(parser);
            this.operator = operator.getTokenType();
        }
        normalize(this);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal leftConditionResult = leftCondition.execute(executor, scope);

        if (rightCondition == null) {
            return leftConditionResult;
        } else {
            final Literal rightConditionResult = rightCondition.execute(executor, scope);

            if (this.operator == TokenType.EQUALS) {
                if (leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            } else {
                if (!leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getFalseValue();
                else
                    return LiteralFactory.getTrueValue();
            }
        }
    }
}
