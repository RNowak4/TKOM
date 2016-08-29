package executor;

import lexer.Lexer;
import org.junit.Ignore;
import org.junit.Test;
import parser.Parser;

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

    @Test
    @Ignore
    public void parseParametersTest() {
    }

}
