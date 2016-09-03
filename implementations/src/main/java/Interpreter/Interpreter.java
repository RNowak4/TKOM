package interpreter;

import executor.Executor;
import lexer.Lexer;
import parser.Parser;
import structures.Program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Interpreter {
    private File scriptFile;

    public Interpreter(final String fileName) {
        scriptFile = new File(fileName);
    }

    public void run() throws FileNotFoundException {
        final InputStream inputStream = new FileInputStream(scriptFile);
        final Lexer lexer = new Lexer(inputStream);
        final Parser parser = new Parser(lexer);
        final Program program = parser.parse();
        final Executor executor = new Executor(program);
        executor.execute();
    }

    public static void runFile(final String fileName) throws FileNotFoundException {
        final File file = new File(fileName);
        final InputStream inputStream = new FileInputStream(file);
        final Lexer lexer = new Lexer(inputStream);
        final Parser parser = new Parser(lexer);
        final Program program = parser.parse();
        final Executor executor = new Executor(program);
        executor.execute();
    }
}
