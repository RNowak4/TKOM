package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;

public class AssignStatement extends Parsable implements Executable {
    private Variable variable;
    private Parsable value;

    public AssignStatement(Variable variable, Parsable value) {
        this.variable = variable;
        this.value = value;
    }

    public AssignStatement() {
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Parsable getValue() {
        return value;
    }

    public void setValue(Parsable value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return Type.Assignment;
    }

    @Override
    public void parse(Parser parser) {

    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal newVal = ((Executable) value).execute(executor, scope);

        variable.setValue(newVal);
        scope.addVariable(variable);

        return newVal;
    }
}
