package parser.structures;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Function> functions = new ArrayList<>();

    public List<Function> getFunctions() {
        return functions;
    }

    public void addFunction(final Function function) {
        functions.add(function);
    }
}
