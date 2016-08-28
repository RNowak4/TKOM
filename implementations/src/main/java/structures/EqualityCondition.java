package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class EqualityCondition extends Parsable implements Executable {
    private boolean negated = false;
    private RelationalCondition leftCondition = new RelationalCondition();
    private RelationalCondition rightCondition;
    private TokenType equalityOp;

    public RelationalCondition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(RelationalCondition leftCondition) {
        this.leftCondition = leftCondition;
    }

    public RelationalCondition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(RelationalCondition rightCondition) {
        this.rightCondition = rightCondition;
    }

    public TokenType getEqualityOp() {
        return equalityOp;
    }

    public void setEqualityOp(TokenType equalityOp) {
        this.equalityOp = equalityOp;
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
            rightCondition = new RelationalCondition();
            rightCondition.parse(parser);
            equalityOp = operator.getTokenType();
        }
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal leftConditionResult = leftCondition.execute(executor, scope);

        if (rightCondition == null) {
            return leftConditionResult;
        } else {
            final Literal rightConditionResult = rightCondition.execute(executor, scope);

            if (equalityOp == TokenType.EQUALS) {
                if (leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            } else {
                if (!leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            }
        }
    }
}
