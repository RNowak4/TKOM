package utils.structures;

import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Expression extends Node {
    private List<Node> operands = new ArrayList<>();
    private List<TokenType> operations = new ArrayList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    public List<Node> getOperands() {
        return operands;
    }

    public List<TokenType> getOperations() {
        return operations;
    }

    @Override
    public Type getType() {
        return Type.Expression;
    }
}
