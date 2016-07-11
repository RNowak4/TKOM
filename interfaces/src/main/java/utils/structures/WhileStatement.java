package utils.structures;

public class WhileStatement extends Node {
    private Condition condition;
    private StatementBlock statementBlock;

    public WhileStatement() {
    }

    public WhileStatement(Condition condition, StatementBlock statementBlock) {
        this.condition = condition;
        this.statementBlock = statementBlock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    @Override
    public Type getType() {
        return Type.WhileStatement;
    }
}
