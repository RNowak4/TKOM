package structures;

import parser.Parser;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parameters extends ParseElement {
    private List<Variable> parameters = new ArrayList<>();

    @Override
    public void parse(Parser parser) {
        parser.accept(TokenType.PARENTH_OPEN);
        parser.checkAccept(TokenType.PARENTH_CLOSE, TokenType.ID, TokenType.RE_NUMBER, TokenType.IM_NUMBER,
                TokenType.ADD, TokenType.SUB);
        if (!parser.checkNextTokenType(TokenType.PARENTH_CLOSE)) {
            parseNextParam(parser);
            while (parser.accept(TokenType.COMMA, TokenType.PARENTH_CLOSE).getTokenType() != TokenType.PARENTH_CLOSE) {
                parseNextParam(parser);
            }
        } else
            parser.accept(TokenType.PARENTH_CLOSE);
    }

    private void parseNextParam(final Parser parser) {
        if (parser.checkNextTokenType(TokenType.ID)) {
            final Variable var = new Variable();
            var.parse(parser);
            parameters.add(var);
        } else {
            final Literal lit = new Literal();
            lit.parse(parser);
            parameters.add(new Variable(lit));
        }
    }

    public List<Variable> getAll() {
        return parameters;
    }
}
