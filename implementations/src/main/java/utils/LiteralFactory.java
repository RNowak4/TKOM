package utils;

import structures.Literal;

public class LiteralFactory {

    public static Literal getTrueValue() {
        return new Literal(1, 1);
    }

    public static Literal getFalseValue() {
        return new Literal(0, 0);
    }
}
