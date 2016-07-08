package utils.structures;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Node {
    private String name;
    private List<Node> arguments = new ArrayList<>();

    public FunctionCall(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public void addArgument(final Node argument) {
        arguments.add(argument);
    }

    @Override
    public Type getType() {
        return Type.FunctionCall;
    }
}
