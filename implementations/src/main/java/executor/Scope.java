package executor;

import structures.Literal;
import structures.Variable;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private boolean isReturn;
    private int breaks;
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

    public Scope getCopy() {
        final Scope copy = new Scope();

        for (String varName : variables.keySet()) {
            copy.addVariable(varName, variables.get(varName));
        }

        return copy;
    }

    public boolean isBreak() {
        if (breaks > 0) {
            --breaks;
            return true;
        } else
            return false;
    }

    public void addBreak() {
        ++breaks;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public void setIfExist(Variable variable) {
        final Literal found = variables.get(variable.getName());
        if (found == null)
            throw new RuntimeException("No such variable declared in the scope!");
        else
            variables.put(variable.getName(), variable.getValue());
    }
}
