package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class RelationalCondition extends ParseTree {
    private ParseTree leftCondition = new PrimaryCondition();
    private ParseTree rightCondition = null;
    private TokenType relationalOp = TokenType.UNDEFINED;

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
            rightCondition = new RelationalCondition();
            rightCondition.parse(parser);
        }
        normalize(this);
    }

    public TokenType getRelationalOp() {
        return relationalOp;
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
//        normalize(this);
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

    @Override
    public ParseTree getLeftParseTree() {
        return leftCondition;
    }

    @Override
    public ParseTree getRightParseTree() {
        return rightCondition;
    }

    @Override
    public void setLeftParseTree(ParseTree parseTree) {
        this.leftCondition = parseTree;
    }

    @Override
    public TokenType getOperator() {
        return relationalOp;
    }

    @Override
    public void setOperator(TokenType operator) {
        this.relationalOp = operator;
    }

    @Override
    public void setRightParseTree(ParseTree parseTree) {
        this.rightCondition = parseTree;
    }
}
