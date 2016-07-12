package parser.structures;

import parser.Parser;
import utils.TokenType;

public class WhileStatement extends Node {
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
        parser.accept(TokenType.PARENTH_OPEN);
        condition.parse(parser);
        parser.accept(TokenType.PARENTH_CLOSE);
        statementBlock.parse(parser);
    }
}
