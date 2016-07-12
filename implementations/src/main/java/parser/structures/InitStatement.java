package parser.structures;

import parser.Parser;
import utils.TokenType;

public class InitStatement extends Node {
    private Variable variable = new Variable();
    private Node assignment;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Node getAssignment() {
        return assignment;
    }

    public void setAssignment(Node assignment) {
        this.assignment = assignment;
    }

    @Override
    public Type getType() {
        return Type.InitStatement;
    }

    @Override
    public void parse(final Parser parser) {
        parser.accept(TokenType.DEF);
        variable.parse(parser);
        if (parser.checkNextTokenType(TokenType.ASSIGN)) {
            parser.accept();
            assignment = parser.parseAssignable();
        }
    }
}
