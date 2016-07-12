package utils;

public class Token {
    private String tokenString;
    private TokenType tokenType = TokenType.UNDEFINED;

    public Token(String tokenString, TokenType tokenType) {
        this.tokenString = tokenString.toLowerCase();
        this.tokenType = tokenType;
    }

    public String getTokenString() {
        return tokenString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (tokenString != null ? !tokenString.equals(token.tokenString) : token.tokenString != null) return false;
        return tokenType == token.tokenType;

    }

    public int hashCode() {
        int result = tokenString != null ? tokenString.hashCode() : 0;
        result = 31 * result + (tokenType != null ? tokenType.hashCode() : 0);
        return result;
    }
}
