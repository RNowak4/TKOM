package parser.structures;

import parser.Parser;

public abstract class Node {
    protected static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Node.class);
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
        Function, PrimaryCondition, RelationalCondition, EqualityCondition, AndCondition, MultiplicativeExpression, PrimaryExpression, WhileStatement
    }

    public Node getParent() {
        return parent;
    }

    public abstract Type getType();

    abstract public void parse(final Parser parser);
}
