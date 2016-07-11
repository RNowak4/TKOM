package utils.structures;

public class InitStatement extends Node {
    private Variable variable;
    private Node assignment;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Node getAssignment() {
        return assignment;
    }

    public void setAssignment(Node assignment) {
        this.assignment = assignment;
    }

    @Override
    public Type getType() {
        return Type.InitStatement;
    }
}
