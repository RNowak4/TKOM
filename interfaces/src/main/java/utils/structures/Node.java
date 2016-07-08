package utils.structures;

public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Condition,
        Expression,
        FunctionCall,
        IfStatement,
        ReturnStatement,
        StatementBlock,
        Variable,
        WhileStatement
    }

    public Node getParent() {
        return parent;
    }

    public abstract Type getType();
}
