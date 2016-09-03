package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.TokenType;

public class AndCondition extends ParseTree {
    private ParseTree leftCondition = new EqualityCondition();
    private ParseTree rightCondition;
    private TokenType operator = TokenType.AND;

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
        return operator;
    }

    @Override
    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    @Override
    public void setRightParseTree(ParseTree parseTree) {
        this.rightCondition = parseTree;
    }
}
