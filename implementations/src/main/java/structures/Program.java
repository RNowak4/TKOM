package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program extends ParseElement implements Executable {
    private Map<String, Function> functions = new HashMap<>();

    @Override
    public void parse(Parser parser) {
        while (!parser.checkNextTokenType(TokenType.END)) {
            Function function = new Function();
            function.parse(parser);
            if (function.isEmpty())
                break;

            functions.put(function.getName(), function);
        }
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Function mainFunction = functions.get("main");
        if (mainFunction == null)
            throw new RuntimeException("No main funcion!");

        return mainFunction.execute(executor, scope, new ArrayList<>());
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
