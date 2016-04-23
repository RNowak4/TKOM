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

        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.DIGIT);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.DIGIT);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.DIGIT);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.ID);
        Assert.assertTrue(lexer.nextToken().getTokenType() == TokenType.UNDEFINED);
    }
}
