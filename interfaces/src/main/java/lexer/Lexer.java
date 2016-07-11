package lexer;

import utils.Token;

public interface Lexer {
    int getCharPosition();

    int getLine();

    Token nextToken();
}
