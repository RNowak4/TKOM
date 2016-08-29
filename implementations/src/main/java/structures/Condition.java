package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.TokenType;

public class Condition extends Parsable implements Executable {
    private AndCondition leftCondition = new AndCondition();
    // TODO change co Condition rightCondition
    private AndCondition rightCondition = null;

    public AndCondition getLeftCondition() {
        return leftCondition;
    }

    public AndCondition getRightCondition() {
        return rightCondition;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.OR)) {
            parser.accept();
            rightCondition = new AndCondition();
            rightCondition.parse(parser);
        }
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        if (leftCondition.execute(executor, scope).isTrue()) {
            return LiteralFactory.getTrueValue();
        } else if (rightCondition != null) {
            if (rightCondition.execute(executor, scope).isTrue()) {
                return LiteralFactory.getTrueValue();
            } else {
                return LiteralFactory.getFalseValue();
            }
        }
        return LiteralFactory.getFalseValue();
    }
}
