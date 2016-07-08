package utils.structures;

public class Variable extends Node {
    private String name;
    private double value;
    private double imValue;

    public Variable(String name, double value, double imValue) {
        this.name = name;
        this.value = value;
        this.imValue = imValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setImValue(double imValue) {
        this.imValue = imValue;
    }

    @Override
    public Type getType() {
        return Type.Variable;
    }
}
