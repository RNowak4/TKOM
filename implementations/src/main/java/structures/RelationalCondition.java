package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class RelationalCondition extends Parsable implements Executable {
    private PrimaryCondition leftCondition = new PrimaryCondition();
    private PrimaryCondition rightCondition = null;
    private TokenType relationalOp = TokenType.UNDEFINED;

    public PrimaryCondition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(PrimaryCondition leftCondition) {
        this.leftCondition = leftCondition;
    }

    public PrimaryCondition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(PrimaryCondition rightCondition) {
        this.rightCondition = rightCondition;
    }

    @Override
    public Type getType() {
        return Type.RelationalCondition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER, TokenType.GREATER_EQUALS)) {
            final Token operator = parser.accept();
            relationalOp = operator.getTokenType();
            rightCondition = new PrimaryCondition();
            rightCondition.parse(parser);
        }
    }

    public TokenType getRelationalOp() {
        return relationalOp;
    }

    public void setRelationalOp(TokenType relationalOp) {
        this.relationalOp = relationalOp;
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal leftConditionResult = leftCondition.execute(executor, scope);

        if (rightCondition == null) {
            return leftConditionResult;
        } else {
            final Literal rightConditionResult = rightCondition.execute(executor, scope);

            if (relationalOp == TokenType.LOWER) {
                return checkLower(leftConditionResult, rightConditionResult);
            } else if (relationalOp == TokenType.LOWER_EQUALS) {
                if (checkLower(leftConditionResult, rightConditionResult).isTrue())
                    return LiteralFactory.getTrueValue();
                else if (leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            } else if (relationalOp == TokenType.GREATER) {
                return checkGreater(leftConditionResult, rightConditionResult);
            } else if (relationalOp == TokenType.GREATER_EQUALS) {
                if (checkGreater(leftConditionResult, rightConditionResult).isTrue())
                    return LiteralFactory.getTrueValue();
                else if (leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            } else
                throw new RuntimeException("No operator!");
        }

    }

    private Literal checkGreater(Literal leftConditionResult, Literal rightConditionResult) {
        if (leftConditionResult.getReValue() > rightConditionResult.getReValue()) {
            return LiteralFactory.getTrueValue();
        } else if (leftConditionResult.getReValue() == leftConditionResult.getReValue()) {
            if (leftConditionResult.getImValue() > leftConditionResult.getImValue())
                return LiteralFactory.getTrueValue();
            else
                return LiteralFactory.getFalseValue();
        } else {
            return LiteralFactory.getFalseValue();
        }
    }

    private Literal checkLower(Literal leftConditionResult, Literal rightConditionResult) {
        if (leftConditionResult.getReValue() < rightConditionResult.getReValue()) {
            return LiteralFactory.getTrueValue();
        } else if (leftConditionResult.getReValue() == leftConditionResult.getReValue()) {
            if (leftConditionResult.getImValue() < leftConditionResult.getImValue())
                return LiteralFactory.getTrueValue();
            else
                return LiteralFactory.getFalseValue();
        } else {
            return LiteralFactory.getFalseValue();
        }
    }
}
