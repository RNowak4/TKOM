package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.LiteralFactory;
import utils.Token;
import utils.TokenType;

public class PrimaryCondition extends Condition {
    private ParseElement operand;
    boolean negated;

    public ParseElement getOperand() {
        return operand;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
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
                operand = new OrCondition();
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

    @Override
    public Literal execute(Executor executor, Scope scope) {
        final Executable executableOperand = (Executable) operand;
        final Literal result = executableOperand.execute(executor, scope);

        if (!negated && result.isTrue())
            return result;
        else if (!negated)
            return LiteralFactory.getFalseValue();
        else if (negated && result.isTrue())
            return LiteralFactory.getFalseValue();
        else
            return LiteralFactory.getTrueValue();
    }
}
