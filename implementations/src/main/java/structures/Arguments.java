package structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Arguments extends ParseElement {
    private List<String> arguments = new ArrayList<>();

    @Override
    public void parse(Parser parser) {
        parser.accept(TokenType.PARENTH_OPEN);
        Token token = parser.accept(TokenType.PARENTH_CLOSE, TokenType.ID);
        if (token.getTokenType() != TokenType.PARENTH_CLOSE) {
            arguments.add(token.getTokenString());
            while (parser.accept(TokenType.COMMA, TokenType.PARENTH_CLOSE).getTokenType() != TokenType.PARENTH_CLOSE) {
                token = parser.accept(TokenType.ID);
                arguments.add(token.getTokenString());
            }
        }
    }

    public List<String> getAll() {
        return arguments;
    }

    public String get(int i) {
        return arguments.get(i);
    }
}
