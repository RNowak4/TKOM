package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class RelationalCondition extends Node {
    private PrimaryCondition leftCondition = new PrimaryCondition();
    private PrimaryCondition rightCondition = null;
    private TokenType relationalOp = TokenType.UNDEFINED;

    public PrimaryCondition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(PrimaryCondition leftCondition) {
        this.leftCondition = leftCondition;
    }

    public PrimaryCondition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(PrimaryCondition rightCondition) {
        this.rightCondition = rightCondition;
    }

    @Override
    public Type getType() {
        return Type.RelationalCondition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.LOWER, TokenType.LOWER_EQUALS, TokenType.GREATER, TokenType.GREATER_EQUALS)) {
            final Token operator = parser.accept();
            relationalOp = operator.getTokenType();
            rightCondition = new PrimaryCondition();
            rightCondition.parse(parser);
        }
    }

    public TokenType getRelationalOp() {
        return relationalOp;
    }

    public void setRelationalOp(TokenType relationalOp) {
        this.relationalOp = relationalOp;
    }
}
