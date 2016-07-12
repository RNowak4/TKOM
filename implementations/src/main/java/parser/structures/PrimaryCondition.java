package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class PrimaryCondition extends Node {
    private Node operand;
    boolean negated;

    public Node getOperand() {
        return operand;
    }

    public void setOperand(Node operand) {
        this.operand = operand;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    @Override
    public Type getType() {
        return Type.PrimaryCondition;
    }

    @Override
    public void parse(final Parser parser) {
        if (parser.checkNextTokenType(TokenType.UNARY)) {
            setNegated(true);
            parser.accept();
        }
        Token token = parser.checkAccept(TokenType.PARENTH_OPEN, TokenType.ID, TokenType.ADD, TokenType.SUB, TokenType.RE_NUMBER, TokenType.IM_NUMBER);
        switch (token.getTokenType()) {
            case PARENTH_OPEN: {
                parser.accept();
                operand = new Condition();
                operand.parse(parser);
                parser.accept(TokenType.PARENTH_CLOSE);
            }
            break;

            case ID: {
                operand = new Variable();
                operand.parse(parser);
            }
            break;

            case ADD:
            case SUB:
            case RE_NUMBER:
            case IM_NUMBER: {
                operand = new Literal();
                operand.parse(parser);
            }
            break;

            default:
                throw new RuntimeException("You shouldn't see that message. Sth is broken.");
        }
    }
}
