package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Variable extends Node {
    private Literal value;
    private String name;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
    }

    public Literal getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.Variable;
    }

    @Override
    public void parse(final Parser parser) {
        final Token token = parser.accept(TokenType.ID);
        name = token.getTokenString();
    }
}
