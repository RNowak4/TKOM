package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class PrimaryExpression extends Parsable {
    private Parsable expression;

    public Parsable getExpression() {
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
                expression = parser.parseFunctionCallOrVariable();
            }
            break;

            case RE_NUMBER:
            case IM_NUMBER:
            case SUB:
            case ADD: {
                expression = new Literal();
                expression.parse(parser);
            }
            break;

            case PARENTH_OPEN: {
                parser.accept();
                expression = new Expression();
                expression.parse(parser);
                parser.accept(TokenType.PARENTH_CLOSE);
            }
            break;

            default:
                throw new RuntimeException("You shouldn't see that message. Sth is broken.");
        }
    }

    public Literal execute(Executor executor, Scope scope) {
        final Executable executable = (Executable) expression;

        return executable.execute(executor, scope);
    }
}
