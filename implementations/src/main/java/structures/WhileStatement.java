package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class WhileStatement extends Parsable implements Executable {
    private Condition condition = new Condition();
    private StatementBlock statementBlock = new StatementBlock();

    public WhileStatement() {
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    @Override
    public Type getType() {
        return Type.WhileStatement;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept();
        parser.accept(TokenType.PARENTH_OPEN);
        condition.parse(parser);
        parser.accept(TokenType.PARENTH_CLOSE);
        statementBlock.parse(parser);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        Literal result = null;

        while (condition.execute(executor, scope).isTrue()) {
            result = statementBlock.execute(executor, scope);
        }

        return result;
    }
}
