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

    public TokenType getOperator() {
        return operator;
    }

    @Override
    public Type getType() {
        return Type.Condition;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Condition condition = (Condition) o;

        if (negated != condition.negated) return false;
        if (operator != condition.operator) return false;
        return !(operands != null ? !operands.equals(condition.operands) : condition.operands != null);

    }

    @Override
    public int hashCode() {
        int result = (negated ? 1 : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (operands != null ? operands.hashCode() : 0);
        return result;
    }
}
