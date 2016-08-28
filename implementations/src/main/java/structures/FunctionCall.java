package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Parsable implements Executable {
    private String name;
    private List<Parsable> arguments = new ArrayList<>();

    public FunctionCall(String name) {
        this.name = name;
    }

    public FunctionCall(String name, List<Parsable> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public FunctionCall() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArguments(List<Parsable> arguments) {
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<Parsable> getArguments() {
        return arguments;
    }

    public void addArgument(final Parsable argument) {
        arguments.add(argument);
    }

    @Override
    public Type getType() {
        return Type.FunctionCall;
    }

    @Override
    public void parse(final Parser parser) {
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Function function = executor.getFunction(name);

        final List<Literal> parameters = new ArrayList<>();
        for (Parsable argument : arguments) {
            Executable executable = (Executable) argument;
            parameters.add(executable.execute(executor, scope));
        }

        return function.execute(executor, scope, parameters);

    }
}
