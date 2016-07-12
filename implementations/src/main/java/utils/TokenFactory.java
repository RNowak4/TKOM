package utils;

public class TokenFactory {

    public static Token getToken(final String tokenText) {
        return new Token(tokenText, TokenType.UNDEFINED);
    }

    public static Token getToken(final TokenType tokenType) {
        return new Token("", tokenType);
    }

    public static Token getToken(final String tokenText, final TokenType tokenType) {
        return new Token(tokenText, tokenType);
    }
}
