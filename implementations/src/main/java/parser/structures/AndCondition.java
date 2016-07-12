package parser.structures;

import parser.Parser;
import utils.TokenType;

public class AndCondition extends Node {
    private EqualityCondition leftCondition = new EqualityCondition();
    private EqualityCondition rightCondition;

    public EqualityCondition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(EqualityCondition leftCondition) {
        this.leftCondition = leftCondition;
    }

    public EqualityCondition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(EqualityCondition rightCondition) {
        this.rightCondition = rightCondition;
    }

    @Override
    public Type getType() {
        return Type.AndCondition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.AND)) {
            parser.accept();
            rightCondition = new EqualityCondition();
            rightCondition.parse(parser);
        }
    }
}
