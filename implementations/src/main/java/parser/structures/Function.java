package parser.structures;

import parser.Parser;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Function extends Node {
    private String name;
    private StatementBlock statementBlock = new StatementBlock();
    private List<String> parameters = new ArrayList<>();

    public Function() {
    }

    public Function(String name, StatementBlock statementBlock) {
        this.name = name;
        this.statementBlock = statementBlock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    @Override
    public Type getType() {
        return Type.Function;
    }

    @Override
    public void parse(final Parser parser) {
        log.trace("Started parsing function.");
        if (parser.accept(TokenType.FUNCTION, TokenType.END).getTokenType() == TokenType.END)
            return ;

        name = parser.accept(TokenType.ID).getTokenString();
        parameters = parser.parseParameters();
        statementBlock.parse(parser);
        log.trace("Successfully parsed function " + this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(name)
                .append("(");

        parameters.stream().forEach(param -> {
            sb.append(param);
            sb.append(",");
        });

        sb.append(")");

        return sb.toString();
    }
}
