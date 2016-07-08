package utils.structures;

public class IfStatement extends Node {
    private Condition condition;
    private StatementBlock trueBlock;
    private StatementBlock elseBlock;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public StatementBlock getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public StatementBlock getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(StatementBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    @Override
    public Type getType() {
        return Type.IfStatement;
    }
}
