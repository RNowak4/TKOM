package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class PrintStatement extends ParseElement implements Executable {
    private Parameters parameters = new Parameters();

    @Override
    public void parse(Parser parser) {
        parser.accept();
        if (parser.checkNextTokenType(TokenType.PARENTH_OPEN)) {
            parameters.parse(parser);
        } else
            parser.accept(TokenType.PARENTH_OPEN);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        for (Variable argument : parameters.getAll()) {
            System.out.println(scope.getValue(argument.getName()));
        }

        return null;
    }
}
