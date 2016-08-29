package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

import java.util.List;
import java.util.stream.Collectors;

public class PrintStatement extends Parsable implements Executable {
    private List<Variable> arguments;

    @Override
    public Type getType() {
        return Type.PrintStatement;
    }

    @Override
    public void parse(Parser parser) {
        parser.accept();
        if (parser.checkNextTokenType(TokenType.PARENTH_OPEN)) {
            arguments = parser.parseParameters().stream()
                    .map(Variable::new).collect(Collectors.toList());
        } else
            parser.accept(TokenType.PARENTH_OPEN);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        for (Variable argument : arguments) {
            System.out.println(scope.getValue(argument.getName()));
        }

        return null;
    }
}
