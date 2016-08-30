import Interpreter.Interpreter;

import java.io.FileNotFoundException;

public class MainClass {

    public static void main(String[] args) {
        Runnable r = () -> {
            try {
                Interpreter.runFile("script.rad");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };

        new Thread(r).start();
    }
}
