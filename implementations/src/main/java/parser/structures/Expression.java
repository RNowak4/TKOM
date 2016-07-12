package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Expression extends Node {
    private MultiplicativeExpression leftExpression = new MultiplicativeExpression();
    private MultiplicativeExpression rightExpression;
    private TokenType operator = TokenType.UNDEFINED;

    public MultiplicativeExpression getLeftExpression() {
        return leftExpression;
    }

    public MultiplicativeExpression getRightExpression() {
        return rightExpression;
    }

    public TokenType getOperator() {
        return operator;
    }

    @Override
    public Type getType() {
        return Type.Expression;
    }

    @Override
    public void parse(final Parser parser) {
        leftExpression.parse(parser);
        if (parser.checkNextTokenType(TokenType.ADD, TokenType.SUB)) {
            final Token token = parser.accept();
            operator = token.getTokenType();
            rightExpression = new MultiplicativeExpression();
            rightExpression.parse(parser);
        }
    }
}
