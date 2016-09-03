package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.TokenType;

public class AndCondition extends Condition {

    public AndCondition() {
        this.leftCondition = new EqualityCondition();
        this.operator = TokenType.AND;
    }

    @Override
    public Type getType() {
        return Type.AndCondition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.AND)) {
            parser.accept();
            rightCondition = new AndCondition();
            rightCondition.parse(parser);
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
            if (rightConditionResult.isTrue() && leftConditionResult.isTrue())
                return LiteralFactory.getTrueValue();
            else
                return LiteralFactory.getFalseValue();
        }
    }
}
