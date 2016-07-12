package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class MultiplicativeExpression extends Node {
    private PrimaryExpression leftExpression = new PrimaryExpression();
    private PrimaryExpression rightExpression;
    private TokenType addOperator = TokenType.UNDEFINED;

    public PrimaryExpression getLeftExpression() {
        return leftExpression;
    }

    public PrimaryExpression getRightExpression() {
        return rightExpression;
    }

    public TokenType getAddOperator() {
        return addOperator;
    }

    @Override
    public Type getType() {
        return Type.MultiplicativeExpression;
    }

    @Override
    public void parse(final Parser parser) {
        leftExpression.parse(parser);
        if (parser.checkNextTokenType(TokenType.MUL, TokenType.DIV, TokenType.MODULO)) {
            final Token token = parser.accept();
            addOperator = token.getTokenType();
            rightExpression = new PrimaryExpression();
            rightExpression.parse(parser);
        }
    }
}
