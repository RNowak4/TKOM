package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class PrimaryExpression extends Node {
    private Node expression;

    public Node getExpression() {
        return expression;
    }

    @Override
    public Type getType() {
        return Type.PrimaryExpression;
    }

    @Override
    public void parse(final Parser parser) {
        final Token token = parser.checkAccept(TokenType.ID, TokenType.RE_NUMBER, TokenType.IM_NUMBER, TokenType.SUB, TokenType.ADD,
                TokenType.PARENTH_OPEN);
        switch (token.getTokenType()) {
            case ID: {
                expression = new Variable();
            }
            break;

            case RE_NUMBER:
            case IM_NUMBER:
            case SUB:
            case ADD: {
                expression = new Literal();
            }
            break;

            case PARENTH_OPEN: {
                parser.accept();
                expression = new Expression();
                parser.accept(TokenType.PARENTH_CLOSE);
            }
            break;

            default:
                throw new RuntimeException("You shouldn't see that message. Sth is broken.");
        }

        expression.parse(parser);
    }
}
