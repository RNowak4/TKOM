package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;

public class AssignStatement extends ParseElement implements Executable {
    private Variable variable;
    private ParseElement value;

    public AssignStatement(Variable variable, ParseElement value) {
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

    public ParseElement getValue() {
        return value;
    }

    public void setValue(ParseElement value) {
        this.value = value;
    }

    @Override
    public void parse(Parser parser) {

    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal newVal = ((Executable) value).execute(executor, scope);

        variable.setValue(newVal);
//        scope.addVariable(variable);
        scope.setIfExist(variable);

        return newVal;
    }
}
