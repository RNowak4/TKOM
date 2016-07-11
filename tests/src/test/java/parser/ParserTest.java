package parser;

import lexer.Lexer;
import lexer.LexerImpl;
import org.junit.Assert;
import org.junit.Test;
import utils.TokenType;
import utils.structures.Condition;
import utils.structures.Literal;
import utils.structures.ReturnStatement;
import utils.structures.Variable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserTest {

    private InputStream getInputStream(final String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    private ParserImpl getParser(final String string) {
        final InputStream inputStream = getInputStream(string);
        final Lexer lexer = new LexerImpl(inputStream);
        final ParserImpl parser = new ParserImpl(lexer);

        return parser;
    }

    @Test
    public void parseParametersTest() {
        final String text = "()";
        final ParserImpl parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        Assert.assertEquals(0, parameters.size());
    }

    @Test
    public void parseParametersTest2() {
        final String text = "(a)";
        final ParserImpl parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        List<String> goodParams = Stream.of(text.substring(1, text.length() - 1).split(",")).collect(Collectors.toList());
        Assert.assertEquals(goodParams, parameters);
    }

    @Test
    public void parseParametersTest3() {
        final String text = "(a,b,c,d)";
        final ParserImpl parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        List<String> goodParams = Stream.of(text.substring(1, text.length() - 1).split(",")).collect(Collectors.toList());
        Assert.assertEquals(goodParams, parameters);
    }

    @Test
    public void parseLiteralTest() {
        final String text = "123";
        final ParserImpl parser = getParser(text);

        final Literal literal = parser.parseLiteral();
        Assert.assertEquals(123, literal.getReValue());
        Assert.assertEquals(0, literal.getImValue());
    }

    @Test
    public void parseLiteralTest2() {
        final String text = "-123 - j";
        final ParserImpl parser = getParser(text);

        final Literal literal = parser.parseLiteral();
        Assert.assertEquals(-123, literal.getReValue());
        Assert.assertEquals(-1, literal.getImValue());
    }

    @Test
    public void parseLiteralTest3() {
        final String text = "-j";
        final ParserImpl parser = getParser(text);

        final Literal literal = parser.parseLiteral();
        Assert.assertEquals(0, literal.getReValue());
        Assert.assertEquals(-1, literal.getImValue());
    }

    @Test
    public void parsePrimaryCondition() {
        final String text = "abecadlo";
        final ParserImpl parser = getParser(text);

        final Condition condition = parser.parsePrimaryCondition();
        Assert.assertEquals(text, ((Variable) condition.getOperands().get(0)).getName());
    }

    @Test
    public void parsePrimaryCondition2() {
        final String text = "+11-j";
        final ParserImpl parser = getParser(text);

        final Condition condition = parser.parsePrimaryCondition();
        Assert.assertEquals(11, ((Literal) condition.getOperands().get(0)).getReValue());
        Assert.assertEquals(-1, ((Literal) condition.getOperands().get(0)).getImValue());
    }

    @Test
    public void parseRelationalCondition() {
        final String text = "a>=b";
        final ParserImpl parser = getParser(text);

        final Condition condition = parser.parseRelationalCondition();
        final Variable firstOperand = (Variable) ((Condition) condition.getOperands().get(0)).getOperands().get(0);
        final Variable secondOperand = (Variable) ((Condition) condition.getOperands().get(1)).getOperands().get(0);

        Assert.assertEquals("a", firstOperand.getName());
        Assert.assertEquals("b", secondOperand.getName());
        Assert.assertEquals(TokenType.GREATER_EQUALS, condition.getOperator());
    }

    @Test
    public void parseRelationalCondition2() {
        final String text = "!a>=b";
        final ParserImpl parser = getParser(text);

        final Condition condition = parser.parseRelationalCondition();
        final Condition firstCondition = (Condition) condition.getOperands().get(0);
        final Condition secondCondition = (Condition) condition.getOperands().get(1);
        final Variable firstOperand = (Variable) firstCondition.getOperands().get(0);
        final Variable secondOperand = (Variable) secondCondition.getOperands().get(0);

        Assert.assertEquals("a", firstOperand.getName());
        Assert.assertEquals("b", secondOperand.getName());
        Assert.assertEquals(TokenType.GREATER_EQUALS, condition.getOperator());
        Assert.assertTrue(firstCondition.isNegated());
    }

    @Test
    public void parseEqualConditionTest() {
        final String text = "a!=!b";
        final ParserImpl parser = getParser(text);

        final Condition condition = parser.parseEqualityCondition();
        final Condition firstCondition = (Condition) condition.getOperands().get(0);
        final Condition secondCondition = (Condition) condition.getOperands().get(1);
        final Variable firstOperand = (Variable) firstCondition.getOperands().get(0);
        final Variable secondOperand = (Variable) secondCondition.getOperands().get(0);

        Assert.assertEquals("a", firstOperand.getName());
        Assert.assertEquals("b", secondOperand.getName());
        Assert.assertEquals(TokenType.NOT_EQUALS, condition.getOperator());
        Assert.assertTrue(condition.isNegated());
        Assert.assertFalse(firstCondition.isNegated());
        Assert.assertTrue(secondCondition.isNegated());
    }

    @Test
    public void parseAndConditionTest() {
        final String text = "!a==b && c!=!d";
        final ParserImpl parser = getParser(text);

         Condition actual = parser.parseAndCondition();
    }

    @Test
    public void parseReturnStatement() {
        final String text = "return 1 + j;";
        final ParserImpl parser = getParser(text);

        ReturnStatement returnStatement = parser.parseReturnStatement();
        returnStatement = null;
    }

    @Test
    public void parseReturnStatement2() {
        final String text = "return fun(a,b,c);";
        final ParserImpl parser = getParser(text);

        ReturnStatement returnStatement = parser.parseReturnStatement();
        returnStatement = null;
    }
}
