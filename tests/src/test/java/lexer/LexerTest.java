
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
            Assert.assertTrue(lexer.nextToken().getTokenType() == tokenType);
        }
    }

    @Test
    public void keywordTokenTest() {
        for (String keyword : PredefinedTokens.keywords.keySet()) {
            Lexer lexer = new LexerImpl(getInputStream(keyword));
            Assert.assertTrue(PredefinedTokens.keywords.get(keyword) == lexer.nextToken().getTokenType());
        }
    }

    @Test
    public void operatorTokenTest() {
        for (String keyword : PredefinedTokens.operators.keySet()) {
            Lexer lexer = new LexerImpl(getInputStream(keyword));
            Assert.assertTrue(PredefinedTokens.operators.get(keyword) == lexer.nextToken().getTokenType());
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
    public void idTokenTest() {
        final String goodName1 = "abc";
        final String goodName2 = "A232BBasd";
        final String badName1 = "1adas";

        final InputStream inputStream = getInputStream(createInputString(goodName1, goodName2, badName1));
        final Lexer lexer = new LexerImpl(inputStream);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.UNDEFINED);
    }

    @Test
    public void digitTokenTest() {
        final String goodDigit1 = "123";
        final String goodDigit2 = "j";
        final String goodDigit3 = "123j";
        final String badDigit1 = "a123";
        final String badDigit2 = "ja";
        final String badDigit3 = "123ja";

        final String inputString = createInputString(goodDigit1, goodDigit2, goodDigit3, badDigit1, badDigit2, badDigit3);
        final InputStream inputStream = getInputStream(inputString);
        final Lexer lexer = new LexerImpl(inputStream);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.UNDEFINED);
    }

    @Test
    public void complexTokenTest() {
        final String tokens = "aaaaa;123+j;";

        final InputStream inputStream = getInputStream(tokens);
        final Lexer lexer = new LexerImpl(inputStream);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ADD);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);
    }

    @Test
    public void complexTokenTest2() {
        final String firstLine = "def a = 123 + 10j;";
        final String secondLine = "def b = 11 - j;";
        final String thirdLine = "return a%b;;";

        final String inputString = createInputString(firstLine, secondLine, thirdLine);
        final InputStream inputStream = getInputStream(inputString);
        final Lexer lexer = new LexerImpl(inputStream);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.DEF);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ASSIGN);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ADD);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.DEF);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ASSIGN);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SUB);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.NUMBER);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.RETURN);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.MODULO);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.SEMICOLON);
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
}
