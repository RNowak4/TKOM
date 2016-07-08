package utils.structures;

import java.util.ArrayList;
import java.util.List;

public class StatementBlock extends Node {
    private List<Node> instructions = new ArrayList<>();

    public List<Node> getInstructions() {
        return instructions;
    }

    public void addInstruction(final Node instruction) {
        instructions.add(instruction);
    }

    @Override
    public Type getType() {
        return Type.StatementBlock;
    }
}
