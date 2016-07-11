package utils;

public class TokenFactory {

    public static Token getToken(final String tokenText) {
        return new TokenImpl(tokenText, TokenType.UNDEFINED);
    }

    public static Token getToken(final TokenType tokenType) {
        return new TokenImpl("", tokenType);
    }

    public static Token getToken(final String tokenText, final TokenType tokenType) {
        return new TokenImpl(tokenText, tokenType);
    }
}
