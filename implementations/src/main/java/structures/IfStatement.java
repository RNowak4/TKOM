package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class IfStatement extends Parsable implements Executable {
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
        parser.accept();
        parser.accept(TokenType.PARENTH_OPEN);
        condition.parse(parser);
        parser.accept(TokenType.PARENTH_CLOSE);
        trueBlock.parse(parser);
        if (parser.getNextToken().getTokenType() == TokenType.ELSE) {
            elseBlock = new StatementBlock();
            parser.accept();
            elseBlock.parse(parser);
        }
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Literal conditionResult = condition.execute(executor, scope);
        if (conditionResult.isTrue()) {
            return trueBlock.execute(executor, scope);
        } else if (elseBlock != null)
            return elseBlock.execute(executor, scope);
        else
            return null;
    }
}
