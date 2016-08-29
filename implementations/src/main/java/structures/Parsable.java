package structures;

import parser.Parser;

public abstract class Parsable {
    protected static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Parsable.class);

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
        Function,
        PrimaryCondition,
        RelationalCondition,
        EqualityCondition,
        AndCondition,
        MultiplicativeExpression,
        PrimaryExpression,
        WhileStatement,
        PrintStatement
    }

    public abstract Type getType();

    abstract public void parse(final Parser parser);
}
