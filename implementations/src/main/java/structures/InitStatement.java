package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class InitStatement extends Parsable implements Executable {
    private Variable variable = new Variable();
    private Parsable assignment;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Parsable getAssignment() {
        return assignment;
    }

    public void setAssignment(Parsable assignment) {
        this.assignment = assignment;
    }

    @Override
    public Type getType() {
        return Type.InitStatement;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept(TokenType.DEF);
        variable.parse(parser);
        if (parser.checkNextTokenType(TokenType.ASSIGN)) {
            parser.accept();
            assignment = parser.parseAssignable();
        }
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal newVal = ((Executable) assignment).execute(executor, scope);

        variable.setValue(newVal);
        scope.addVariable(variable);

        return newVal;
    }
}
