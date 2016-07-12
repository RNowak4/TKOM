package lexer;

import utils.PredefinedTokens;
import utils.Token;
import utils.TokenFactory;
import utils.TokenType;

import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    private char tokenChar;
    private int lineCounter = 1;
    private int charCounter = 1;
    private InputStream inputStream;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private char nextChar() {
        if (isStreamEnd())
            return 0;
        else
            try {
                return (char) inputStream.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return 0;
    }

    private boolean isSpace(final char c) {
        if (c == '\n') {
            charCounter = 1;
            ++lineCounter;
        }
        return c == ' ' || c == '\t' || c == '\n';
    }

    private boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isLiteral(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isStreamEnd() {
        try {
            return inputStream.available() == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isNextOperatorChar(final char firstChar, final char secondChar) {
        return ((secondChar == '=') && (firstChar == '<' || firstChar == '>' || firstChar == '=' || firstChar == '!'))
                || (secondChar == '&' && firstChar == '&') || (secondChar == '|' && firstChar == '|');
    }

    public int getCharPosition() {
        return charCounter;
    }

    public int getLine() {
        return lineCounter;
    }

    public Token nextToken() {
        if (isStreamEnd() && tokenChar == 0)
            return TokenFactory.getToken(TokenType.END);

        if (tokenChar == 0 || isSpace(tokenChar))
            while (isSpace(tokenChar = nextChar()))
                ;

        if (isDigit(tokenChar))
            return processNumber();
        else if (isLiteral(tokenChar))
            return processIdOrNumber();
        else
            return processOperator();
    }

    private Token processOperator() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(tokenChar);

        if (isNextOperatorChar(tokenChar, (tokenChar = nextChar()))) {
            stringBuilder.append(tokenChar);
            if (!isStreamEnd())
                tokenChar = nextChar();
        }

        final String tokenString = stringBuilder.toString().toLowerCase();
        final TokenType operatorTokenType = PredefinedTokens.operators.get(tokenString);

        if (operatorTokenType != null)
            return TokenFactory.getToken(tokenString, operatorTokenType);
        else
            return TokenFactory.getToken(tokenString);
    }

    private Token processIdOrNumber() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(tokenChar);

        while (isLiteral(tokenChar = nextChar()) || isDigit(tokenChar)) {
            stringBuilder.append(tokenChar);
        }

        final String string = stringBuilder.toString().toLowerCase();
        final TokenType keywordToken = PredefinedTokens.keywords.get(string);

        if (keywordToken != null)
            return TokenFactory.getToken(string, keywordToken);
        else if (string.equals("j"))
            return TokenFactory.getToken(string, TokenType.IM_NUMBER);
        else
            return TokenFactory.getToken(string, TokenType.ID);
    }

    private Token processNumber() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(tokenChar);

        while (isDigit(tokenChar = nextChar()))
            stringBuilder.append(tokenChar);

        if (tokenChar == 'j') {
            stringBuilder.append(tokenChar);
            tokenChar = nextChar();
        }

        final String string = stringBuilder.toString();
        if (isLiteral(tokenChar))
            return TokenFactory.getToken(string);
        else if (string.contains("j"))
            return TokenFactory.getToken(string, TokenType.IM_NUMBER);
        else
            return TokenFactory.getToken(string, TokenType.RE_NUMBER);
    }

}
