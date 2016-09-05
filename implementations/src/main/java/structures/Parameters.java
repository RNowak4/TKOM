package structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parameters extends ParseElement {
    private List<Variable> parameters = new ArrayList<>();

    @Override
    public void parse(Parser parser) {
        parser.accept(TokenType.PARENTH_OPEN);
        Token token = parser.accept(TokenType.PARENTH_CLOSE, TokenType.ID);
        if (token.getTokenType() != TokenType.PARENTH_CLOSE) {
            Variable var = new Variable(token.getTokenString());
            parameters.add(var);
            while (parser.accept(TokenType.COMMA, TokenType.PARENTH_CLOSE).getTokenType() != TokenType.PARENTH_CLOSE) {
                token = parser.accept(TokenType.ID);
                var = new Variable(token.getTokenString());
                parameters.add(var);
            }
        }
    }

    public List<Variable> getAll() {
        return parameters;
    }
}
