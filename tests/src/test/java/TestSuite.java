import executor.ExecutorTest;
import lexer.LexerTest;
import org.junit.runners.Suite;
import parser.ParserTest;

@Suite.SuiteClasses({
        LexerTest.class,
        ParserTest.class,
        ExecutorTest.class
})
public class TestSuite {
}
