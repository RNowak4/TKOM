package executor;

import structures.Literal;

public interface Executable {
    Literal execute(Executor executor, Scope scope);
}
