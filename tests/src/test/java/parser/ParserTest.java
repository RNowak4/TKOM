package parser;

import lexer.Lexer;
import org.junit.Assert;
import org.junit.Test;
import structures.*;
import utils.TokenType;

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

    private Parser getParser(final String string) {
        final InputStream inputStream = getInputStream(string);
        final Lexer lexer = new Lexer(inputStream);

        return new Parser(lexer);
    }

    @Test
    public void parseParametersTest() {
        final String text = "()";
        final Parser parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        Assert.assertEquals(0, parameters.size());
    }

    @Test
    public void parseParametersTest2() {
        final String text = "(a)";
        final Parser parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        List<String> goodParams = Stream.of(text.substring(1, text.length() - 1).split(",")).collect(Collectors.toList());
        Assert.assertEquals(goodParams, parameters);
    }

    @Test
    public void parseParametersTest3() {
        final String text = "(a,b,c,d)";
        final Parser parser = getParser(text);

        List<String> parameters = parser.parseParameters();
        List<String> goodParams = Stream.of(text.substring(1, text.length() - 1).split(",")).collect(Collectors.toList());
        Assert.assertEquals(goodParams, parameters);
    }

    @Test
    public void parseLiteralTest() {
        final String text = "123";
        final Parser parser = getParser(text);
        final Literal literal = new Literal();
        literal.parse(parser);

        Assert.assertEquals(123, literal.getReValue());
        Assert.assertEquals(0, literal.getImValue());
    }

    @Test
    public void parseLiteralTest2() {
        final String text = "-123 - j";
        final Parser parser = getParser(text);
        final Literal literal = new Literal();
        literal.parse(parser);

        Assert.assertEquals(-123, literal.getReValue());
        Assert.assertEquals(-1, literal.getImValue());
    }

    @Test
    public void parseLiteralTest3() {
        final String text = "-j";
        final Parser parser = getParser(text);
        final Literal literal = new Literal();
        literal.parse(parser);

        Assert.assertEquals(0, literal.getReValue());
        Assert.assertEquals(-1, literal.getImValue());
    }

    @Test
    public void parsePrimaryCondition() {
        final String text = "abecadlo";
        final Parser parser = getParser(text);
        final PrimaryCondition condition = new PrimaryCondition();
        condition.parse(parser);

        Assert.assertEquals(text, ((Variable) condition.getOperand()).getName());
    }

    @Test
    public void parsePrimaryCondition2() {
        final String text = "+11-j";
        final Parser parser = getParser(text);
        final PrimaryCondition condition = new PrimaryCondition();
        condition.parse(parser);

        Assert.assertEquals(11, ((Literal) condition.getOperand()).getReValue());
        Assert.assertEquals(-1, ((Literal) condition.getOperand()).getImValue());
    }

    @Test
    public void parseRelationalCondition() {
        final String text = "a>=b";
        final Parser parser = getParser(text);
        final RelationalCondition condition = new RelationalCondition();
        condition.parse(parser);

        final Variable firstOperand = (Variable) ((PrimaryCondition) (condition.getLeftParseTree())).getOperand();
        final Variable secondOperand = (Variable) ((PrimaryCondition) (condition.getRightParseTree().getLeftParseTree())).getOperand();

        Assert.assertEquals("a", firstOperand.getName());
        Assert.assertEquals("b", secondOperand.getName());
        Assert.assertEquals(TokenType.GREATER_EQUALS, condition.getRelationalOp());
    }

    @Test
    public void parseRelationalCondition2() {
        final String text = "!a>=b";
        final Parser parser = getParser(text);
        final RelationalCondition condition = new RelationalCondition();
        condition.parse(parser);

        final PrimaryCondition firstCondition = (PrimaryCondition) condition.getLeftParseTree();
        final PrimaryCondition secondCondition = (PrimaryCondition) condition.getRightParseTree().getLeftParseTree();
        final Variable firstOperand = (Variable) firstCondition.getOperand();
        final Variable secondOperand = (Variable) secondCondition.getOperand();

        Assert.assertEquals("a", firstOperand.getName());
        Assert.assertEquals("b", secondOperand.getName());
        Assert.assertEquals(TokenType.GREATER_EQUALS, condition.getRelationalOp());
        Assert.assertTrue(firstCondition.isNegated());
    }

    @Test
    public void parseAndConditionTest() {
        final String text = "!a==b && c!=!d";
        final Parser parser = getParser(text);

        AndCondition condition = new AndCondition();
        condition.parse(parser);
    }

    @Test
    public void parseReturnStatement() {
        final String text = "return 2 + 5j;";
        final Parser parser = getParser(text);

        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.parse(parser);
    }

    @Test
    public void parseReturnStatement2() {
        final String text = "return fun(a,b,c);";
        final Parser parser = getParser(text);

        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.parse(parser);
    }

    @Test
    public void parseInitStatement() {
        final String text = "def a = 5 + j;";
        final Parser parser = getParser(text);

        InitStatement initStatement = new InitStatement();
        initStatement.parse(parser);
    }
}
