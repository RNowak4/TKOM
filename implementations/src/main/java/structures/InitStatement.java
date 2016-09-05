package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class InitStatement extends ParseElement implements Executable {
    private Variable variable = new Variable();
    private ParseElement assignment;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public ParseElement getAssignment() {
        return assignment;
    }

    public void setAssignment(ParseElement assignment) {
        this.assignment = assignment;
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
