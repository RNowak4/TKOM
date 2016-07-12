package parser.structures;

import parser.Parser;
import utils.TokenType;

public class ReturnStatement extends Node {
    private Node returnVal;

    public ReturnStatement() {
    }

    public ReturnStatement(Node returnVal) {
        this.returnVal = returnVal;
    }

    public Node getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Node returnVal) {
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
        }
        else
            returnVal = parser.parseFunctionCallOrVariable();

        parser.accept(TokenType.SEMICOLON);
    }
}
