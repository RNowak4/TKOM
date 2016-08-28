package executor;

import lexer.Lexer;
import org.junit.Test;
import parser.Parser;
import structures.Program;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ExecutorTest {

    private InputStream getInputStream(final String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    private Parser getParser(final String string) {
        final InputStream inputStream = getInputStream(string);
        final Lexer lexer = new Lexer(inputStream);

        return new Parser(lexer);
    }

    // Testowy test. hehe
    @Test
    public void parseParametersTest() {
        final String text = "function main() { def a = 5 + j; def b = 5j; def c = a + b; }";
        final Parser parser = getParser(text);
        final Program program = parser.parse();
        final Executor executor = new Executor(program);
        executor.execute();
    }

}
