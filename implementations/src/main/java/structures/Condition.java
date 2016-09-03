package structures;

import utils.TokenType;

public abstract class Condition extends ParseTree {
    protected Condition leftCondition;
    protected Condition rightCondition;
    protected TokenType operator = TokenType.UNDEFINED;

    public Condition getLeftCondition() {
        return leftCondition;
    }

    public Condition getRightCondition() {
        return rightCondition;
    }

    @Override
    protected ParseTree getLeftParseTree() {
        return leftCondition;
    }

    @Override
    protected ParseTree getRightParseTree() {
        return rightCondition;
    }

    @Override
    protected void setLeftParseTree(ParseTree parseTree) {
        this.leftCondition = (Condition) parseTree;
    }

    @Override
    public TokenType getOperator() {
        return operator;
    }

    @Override
    protected void setOperator(TokenType operator) {
        this.operator = operator;
    }

    @Override
    protected void setRightParseTree(ParseTree parseTree) {
        this.rightCondition = (Condition) parseTree;
    }
}
