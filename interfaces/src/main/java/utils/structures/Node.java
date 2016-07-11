package utils.structures;

public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Condition,
        Expression,
        FunctionCall,
        IfStatement,
        Literal,
        ReturnStatement,
        StatementBlock,
        Variable,
        InitStatement,
        WhileStatement
    }

    public Node getParent() {
        return parent;
    }

    public abstract Type getType();
}
