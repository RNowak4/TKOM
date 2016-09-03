package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class EqualityCondition extends ParseTree {
    private boolean negated = false;
    private ParseTree leftCondition = new RelationalCondition();
    private ParseTree rightCondition;
    private TokenType equalityOp;

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
            equalityOp = operator.getTokenType();
        }
        normalize(this);
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
//        normalize(this);
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
                    return LiteralFactory.getFalseValue();
                else
                    return LiteralFactory.getTrueValue();
            }
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
        return equalityOp;
    }

    @Override
    public void setOperator(TokenType operator) {
        this.equalityOp = operator;
    }

    @Override
    public void setRightParseTree(ParseTree parseTree) {
        this.rightCondition = parseTree;
    }
}
