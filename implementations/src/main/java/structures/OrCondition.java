package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.TokenType;

public class OrCondition extends ParseTree {
    private ParseTree leftCondition = new AndCondition();
    private ParseTree rightCondition = null;
    private TokenType operator = TokenType.OR;

    @Override
    public Type getType() {
        return Type.Condition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.OR)) {
            parser.accept();
            rightCondition = new OrCondition();
            rightCondition.parse(parser);
        }
        normalize(this);
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
