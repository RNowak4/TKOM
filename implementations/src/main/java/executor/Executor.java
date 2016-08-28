package executor;

import structures.Function;
import structures.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Executor {
    private Map<String, Function> functions = new HashMap<>();

    public Executor(final Program program) {
        for (Function function : program.getFunctions()) {
            functions.put(function.getName(), function);
        }
    }

    public void execute() {
        Function mainFunction = functions.get("main");
        if (mainFunction == null)
            throw new RuntimeException("No main function defined");

        mainFunction.execute(this, new Scope(), new ArrayList<>());
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
