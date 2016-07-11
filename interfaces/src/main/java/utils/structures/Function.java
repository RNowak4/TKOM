package utils.structures;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private String name;
    private StatementBlock statementBlock;
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
