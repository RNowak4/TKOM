package lexer;

import utils.PredefinedTokens;
import utils.Token;
import utils.TokenImpl;
import utils.TokenType;

import java.io.IOException;
import java.io.InputStream;

public class LexerImpl implements Lexer {
    private char tokenChar;
    private InputStream inputStream;

    public LexerImpl(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private char nextChar() {
        char nextChar = 0;

        try {
            nextChar = (char) inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nextChar;
    }

    private boolean isSpace(final char c) {
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
        return (secondChar == '=') && (firstChar == '<' || firstChar == '>' || firstChar == '=' || firstChar == '!');
    }

    @Override
    public Token nextToken() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (tokenChar == 0 || isSpace(tokenChar))
            while (!isStreamEnd() && isSpace(tokenChar = nextChar()))
                ;

        stringBuilder.append(tokenChar);
        if (isDigit(tokenChar)) {
            while (!isStreamEnd() && isDigit(tokenChar = nextChar()))
                stringBuilder.append(tokenChar);

            if (tokenChar == 'j') {
                stringBuilder.append(tokenChar);
                tokenChar = nextChar();
            }

            if (isLiteral(tokenChar))
                return new TokenImpl(stringBuilder.toString(), TokenType.UNDEFINED);
            else
                return new TokenImpl(stringBuilder.toString(), TokenType.DIGIT);
        } else if (isLiteral(tokenChar)) {
            while (!isStreamEnd() && isLiteral(tokenChar = nextChar()) || isDigit(tokenChar)) {
                stringBuilder.append(tokenChar);
            }

            final String string = stringBuilder.toString().toLowerCase();
            final TokenType keywordToken = PredefinedTokens.keywords.get(string);

            if (keywordToken != null)
                return new TokenImpl(string, keywordToken);
            else if (string.equals("j"))
                return new TokenImpl(string, TokenType.DIGIT);
            else
                return new TokenImpl(string, TokenType.ID);
        } else {
            if (!isStreamEnd() && isNextOperatorChar(tokenChar, (tokenChar = nextChar()))) {
                stringBuilder.append(tokenChar);
            }

            final String tokenString = stringBuilder.toString().toLowerCase();
            final TokenType operatorTokenType = PredefinedTokens.operators.get(tokenString);

            if (operatorTokenType != null)
                return new TokenImpl(tokenString, operatorTokenType);
            else
                return new TokenImpl(tokenString, TokenType.UNDEFINED);
        }
    }
}
