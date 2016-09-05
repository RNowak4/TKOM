package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends ParseElement implements Executable {
    private String name;
    private Parameters parameters = new Parameters();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void parse(final Parser parser) {
        parameters.parse(parser);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Function function = executor.getFunction(name);

        final List<Literal> parameters = new ArrayList<>();
        for (ParseElement argument : this.parameters.getAll()) {
            Executable executable = (Executable) argument;
            parameters.add(executable.execute(executor, scope));
        }

        return function.execute(executor, scope, parameters);

    }
}
