package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

import java.util.List;

public class Function extends ParseElement {
    private String name;
    private StatementBlock statementBlock = new StatementBlock();
    private Arguments parameters = new Arguments();

    public boolean isEmpty() {
        return name == null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void parse(final Parser parser) {
        log.trace("Started parsing function.");
        if (parser.accept(TokenType.FUNCTION, TokenType.END).getTokenType() == TokenType.END)
            return;

        name = parser.accept(TokenType.ID).getTokenString();
        parameters.parse(parser);
        statementBlock.parse(parser);
        log.trace("Successfully parsed function " + this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(name)
                .append("(");

        parameters.getAll().stream().forEach(param -> {
            sb.append(param);
            sb.append(",");
        });

        sb.append(")");

        return sb.toString();
    }

    public Literal execute(Executor executor, Scope scope, List<Literal> args) {
        if (args.size() != parameters.getAll().size())
            throw new RuntimeException("Bad nubmer of arguments in function call!");

        final Scope functionScope = scope.getCopy();
        for (int i = 0; i < args.size(); i++) {
            functionScope.addVariable(parameters.get(i), args.get(i));
        }

        return statementBlock.execute(executor, functionScope);
    }

}
