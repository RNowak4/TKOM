package utils.structures;

import utils.TokenType;

import java.util.LinkedList;
import java.util.List;

public class Condition extends Node {
    private boolean negated = false;
    private TokenType operator = TokenType.UNDEFINED;
    private List<Node> operands = new LinkedList<>();

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public List<Node> getOperands() {
        return operands;
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }
}
