package executor;

import structures.Function;
import structures.Program;

public class Executor {
    private Program program;

    public Executor(final Program program) {
        this.program = program;
    }

    public void execute() {
        program.execute(this, new Scope());
    }

    public Function getFunction(String name) {
        return program.getFunction(name);
    }
}
