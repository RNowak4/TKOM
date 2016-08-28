package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.TokenType;

public class AndCondition extends Parsable implements Executable {
    private EqualityCondition leftCondition = new EqualityCondition();
    private EqualityCondition rightCondition;

    public EqualityCondition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(EqualityCondition leftCondition) {
        this.leftCondition = leftCondition;
    }

    public EqualityCondition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(EqualityCondition rightCondition) {
        this.rightCondition = rightCondition;
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
            rightCondition = new EqualityCondition();
            rightCondition.parse(parser);
        }
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
