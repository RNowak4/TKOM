package parser.structures;

import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Node {
    private String name;
    private List<Node> arguments = new ArrayList<>();

    public FunctionCall(String name) {
        this.name = name;
    }

    public FunctionCall(String name, List<Node> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public FunctionCall() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArguments(List<Node> arguments) {
        this.arguments = arguments;
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

    @Override
    public void parse(final Parser parser) {
    }
}
