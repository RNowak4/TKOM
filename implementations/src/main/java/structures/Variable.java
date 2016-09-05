package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Variable extends ParseElement implements Executable {
    private Literal value;
    private String name;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
    }

    public Literal getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(Literal value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void parse(final Parser parser) {
        final Token token = parser.accept(TokenType.ID);
        name = token.getTokenString();
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        if (value != null) {
            return value;
        } else {
            return scope.getValue(name);
        }
    }
}
