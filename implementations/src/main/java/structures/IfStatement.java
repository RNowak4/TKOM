package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class IfStatement extends ParseElement implements Executable {
    private OrCondition orCondition = new OrCondition();
    private StatementBlock trueBlock = new StatementBlock();
    private StatementBlock elseBlock = null;

    public OrCondition getOrCondition() {
        return orCondition;
    }

    public void setOrCondition(OrCondition orCondition) {
        this.orCondition = orCondition;
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
    public void parse(final Parser parser) {
        parser.accept();
        parser.accept(TokenType.PARENTH_OPEN);
        orCondition.parse(parser);
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
        final Literal conditionResult = orCondition.execute(executor, scope);
        if (conditionResult.isTrue()) {
            return trueBlock.execute(executor, scope);
        } else if (elseBlock != null)
            return elseBlock.execute(executor, scope);
        else
            return null;
    }
}
