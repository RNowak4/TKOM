package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class RelationalCondition extends Condition {

    public RelationalCondition() {
        this.leftCondition = new PrimaryCondition();
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER, TokenType.GREATER_EQUALS)) {
            final Token operator = parser.accept();
            this.operator = operator.getTokenType();
            rightCondition = new RelationalCondition();
            rightCondition.parse(parser);
        }
        normalize(this);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
//        normalize(this);
        final Literal leftConditionResult = leftCondition.execute(executor, scope);

        if (rightCondition == null) {
            return leftConditionResult;
        } else {
            final Literal rightConditionResult = rightCondition.execute(executor, scope);

            if (operator == TokenType.LOWER) {
                return checkLower(leftConditionResult, rightConditionResult);
            } else if (operator == TokenType.LOWER_EQUALS) {
                if (checkLower(leftConditionResult, rightConditionResult).isTrue())
                    return LiteralFactory.getTrueValue();
                else if (leftConditionResult.equals(rightConditionResult))
                    return LiteralFactory.getTrueValue();
                else
                    return LiteralFactory.getFalseValue();
            } else if (operator == TokenType.GREATER) {
                return checkGreater(leftConditionResult, rightConditionResult);
            } else if (operator == TokenType.GREATER_EQUALS) {
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
