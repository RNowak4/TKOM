package utils;

public class TokenImpl implements Token {
    private String tokenString;
    private TokenType tokenType;

    public TokenImpl(String tokenString, TokenType tokenType) {
        this.tokenString = tokenString.toLowerCase();
        this.tokenType = tokenType;
    }

    @Override
    public String getTokenString() {
        return tokenString;
    }

    @Override
    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenImpl token = (TokenImpl) o;

        if (tokenString != null ? !tokenString.equals(token.tokenString) : token.tokenString != null) return false;
        return tokenType == token.tokenType;

    }

    @Override
    public int hashCode() {
        int result = tokenString != null ? tokenString.hashCode() : 0;
        result = 31 * result + (tokenType != null ? tokenType.hashCode() : 0);
        return result;
    }
}
