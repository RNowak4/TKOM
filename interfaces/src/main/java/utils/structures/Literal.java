package utils.structures;

public class Literal extends Node {
    private int reValue;
    private int imValue;

    public Literal() {
    }

    public Literal(int reValue, int imValue) {
        this.reValue = reValue;
        this.imValue = imValue;
    }

    public int getReValue() {
        return reValue;
    }

    public void setReValue(int reValue) {
        this.reValue = reValue;
    }

    public int getImValue() {
        return imValue;
    }

    public void setImValue(int imValue) {
        this.imValue = imValue;
    }

    @Override
    public Type getType() {
        return Type.Literal;
    }
}
