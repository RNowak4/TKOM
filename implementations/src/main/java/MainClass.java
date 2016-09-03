import interpreter.Interpreter;

import java.io.FileNotFoundException;

public class MainClass {

    public static void main(String[] args) {
        try {
            Interpreter.runFile("script.rad");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
