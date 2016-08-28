package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class ReturnStatement extends Parsable implements Executable {
    private Parsable returnVal;

    public ReturnStatement() {
    }

    public ReturnStatement(Parsable returnVal) {
        this.returnVal = returnVal;
    }

    public Parsable getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Parsable returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public Type getType() {
        return Type.ReturnStatement;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept(TokenType.RETURN);
        if (parser.checkNextTokenType(TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD)) {
            returnVal = new Literal();
            returnVal.parse(parser);
        } else
            returnVal = parser.parseFunctionCallOrVariable();

        parser.accept(TokenType.SEMICOLON);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Executable executable = (Executable) returnVal;

        return executable.execute(executor, scope);
    }
}
