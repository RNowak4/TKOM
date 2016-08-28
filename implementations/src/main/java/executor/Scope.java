package executor;

import structures.Literal;
import structures.Variable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private Map<String, Literal> variables = new HashMap<>();

    public void addVariable(final Variable variable) {
        variables.put(variable.getName(), variable.getValue());
    }

    public void addVariable(final String name, final Literal value) {
        variables.put(name, value);
    }

    public Literal getValue(final String name) {
        return variables.get(name);
    }

}
