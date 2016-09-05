package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.TokenType;

public class ReturnStatement extends ParseElement implements Executable {
    private ParseElement returnVal;

    public ReturnStatement() {
    }

    public ReturnStatement(ParseElement returnVal) {
        this.returnVal = returnVal;
    }

    public ParseElement getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(ParseElement returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept(TokenType.RETURN);
        if (parser.checkNextTokenType(TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD, TokenType.ID, TokenType.PARENTH_OPEN)) {
            returnVal = new AdditiveExpression();
            returnVal.parse(parser);
        } else if (parser.checkNextTokenType(TokenType.SEMICOLON))
            return;
        else
            returnVal = parser.parseFunctionCallOrVariable();

        parser.accept(TokenType.SEMICOLON);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        if (returnVal == null)
            return null;
        final Executable executable = (Executable) returnVal;

        return executable.execute(executor, scope);
    }
}
