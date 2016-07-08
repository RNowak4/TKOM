package utils.structures;

public class ReturnStatement extends Node {
    private Node returnVal;

    public ReturnStatement(Node returnVal) {
        this.returnVal = returnVal;
    }

    public Node getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Node returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public Type getType() {
        return Type.ReturnStatement;
    }
}
