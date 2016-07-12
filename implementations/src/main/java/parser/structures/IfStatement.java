package parser.structures;

import parser.Parser;
import utils.TokenType;

public class IfStatement extends Node {
    private Condition condition = new Condition();
    private StatementBlock trueBlock = new StatementBlock();
    private StatementBlock elseBlock = null;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public StatementBlock getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public StatementBlock getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(StatementBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    @Override
    public Type getType() {
        return Type.IfStatement;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept(TokenType.PARENTH_OPEN);
        condition.parse(parser);
        parser.accept(TokenType.PARENTH_CLOSE);
        trueBlock.parse(parser);
        if (parser.getNextToken().getTokenType() == TokenType.ELSE) {
            elseBlock = new StatementBlock();
            elseBlock.parse(parser);
        }
    }
}
