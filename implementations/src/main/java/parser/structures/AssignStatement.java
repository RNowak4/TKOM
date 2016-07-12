package parser.structures;

import parser.Parser;

public class AssignStatement extends Node {
    private Variable variable;
    private Node value;

    public AssignStatement(Variable variable, Node value) {
        this.variable = variable;
        this.value = value;
    }

    public AssignStatement() {
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Assignment;
    }

    @Override
    public void parse(Parser parser) {

    }
}
