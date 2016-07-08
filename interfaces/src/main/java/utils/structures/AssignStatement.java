package utils.structures;

import com.sun.org.apache.xpath.internal.operations.Variable;

public  class AssignStatement extends  Node {
    private Variable variable;
    private Node value;

    public AssignStatement(Variable variable, Node value) {
        this.variable = variable;
        this.value = value;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Assignment;
    }
}
