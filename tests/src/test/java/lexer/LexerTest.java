
package lexer;

import org.junit.Assert;
import org.junit.Test;
import utils.PredefinedTokens;
import utils.TokenType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LexerTest {

    private InputStream getInputStream(final String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    private void assertLexer(final String string, TokenType... tokenTypes) {
        final InputStream inputStream = getInputStream(string);
        final Lexer lexer = new LexerImpl(inputStream);

        for (TokenType tokenType : tokenTypes) {
            Assert.assertEquals(tokenType, lexer.nextToken().getTokenType());
        }
    }

    @Test
    public void keywordTokenTest() {
        for (String keyword : PredefinedTokens.keywords.keySet()) {
            Lexer lexer = new LexerImpl(getInputStream(keyword));
            Assert.assertEquals(PredefinedTokens.keywords.get(keyword), lexer.nextToken().getTokenType());
        }
    }

    @Test
    public void operatorTokenTest() {
        for (String keyword : PredefinedTokens.operators.keySet()) {
            Lexer lexer = new LexerImpl(getInputStream(keyword));
            Assert.assertEquals(PredefinedTokens.operators.get(keyword), lexer.nextToken().getTokenType());
        }
    }

    private String createInputString(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    @Test
    public void equalsTest() {
        final String testString = "a==b";

        assertLexer(testString, TokenType.ID, TokenType.EQUALS, TokenType.ID);
    }

    @Test
    public void assignTest() {
        final String testString = "a=b";

        assertLexer(testString, TokenType.ID, TokenType.ASSIGN, TokenType.ID);
    }

    @Test
    public void greaterTest() {
        final String testString = "a>b";

        assertLexer(testString, TokenType.ID, TokenType.GREATER, TokenType.ID);
    }

    @Test
    public void simpleNumberTest() {
        final String testString = "123";

        assertLexer(testString, TokenType.NUMBER);
    }

    @Test
    public void simpleNumberTest2() {
        final String testString = "123j";

        assertLexer(testString, TokenType.NUMBER);
    }

    @Test
    public void simpleNumberTest3() {
        final String testString = "j";

        assertLexer(testString, TokenType.NUMBER);
    }

    @Test
    public void bracketsTest() {
        final String testString = "([{}])";

        assertLexer(testString, TokenType.PARENTH_OPEN, TokenType.SQUARE_BRACKET_OPEN,
                TokenType.BRACKET_OPEN, TokenType.BRACKET_CLOSE, TokenType.SQUARE_BRACKET_CLOSE,
                TokenType.PARENTH_CLOSE);
    }

    @Test
    public void undefinedTest() {
        final String testString = "1a";

        assertLexer(testString, TokenType.UNDEFINED);
    }

    @Test
    public void endTokenTest() {
        final String testString = "";

        assertLexer(testString, TokenType.END);
    }

    @Test
    public void endTokenTest2() {
        final String testString = "1";

        assertLexer(testString, TokenType.NUMBER, TokenType.END);
    }

    @Test
    public void complexTest1() {
        final String testString = "def a = 123 + 10j;";

        assertLexer(testString, TokenType.DEF, TokenType.ID, TokenType.ASSIGN, TokenType.NUMBER,
                TokenType.ADD, TokenType.NUMBER, TokenType.SEMICOLON);
    }

    @Test
    public void complexTest2() {
        final String testString = "return a%b;;";

        assertLexer(testString, TokenType.RETURN, TokenType.ID, TokenType.MODULO, TokenType.ID,
                TokenType.SEMICOLON, TokenType.SEMICOLON, TokenType.END);
    }

}
