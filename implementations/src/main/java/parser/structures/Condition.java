package parser.structures;

import parser.Parser;
import utils.TokenType;

public class Condition extends Node {
    private AndCondition leftCondition = new AndCondition();
    // TODO change co Condition rightCondition
    private AndCondition rightCondition = null;

    public AndCondition getLeftCondition() {
        return leftCondition;
    }

    public AndCondition getRightCondition() {
        return rightCondition;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }

    @Override
    public void parse(final Parser parser) {
        leftCondition.parse(parser);
        if (parser.checkNextTokenType(TokenType.OR)) {
            parser.accept();
            rightCondition = new AndCondition();
            rightCondition.parse(parser);
        }
    }
}
