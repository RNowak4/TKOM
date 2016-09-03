package structures;

import executor.Executable;
import utils.TokenType;

public abstract class ParseTree extends Parsable implements Executable {
    public abstract ParseTree getLeftParseTree();

    public abstract ParseTree getRightParseTree();

    public abstract void setLeftParseTree(ParseTree parseTree);

    public abstract TokenType getOperator();

    public abstract void setOperator(TokenType operator);

    public abstract void setRightParseTree(ParseTree parseTree);

    protected static void normalize(final ParseTree parseTree) {
        for (ParseTree rightParseTree = parseTree.getRightParseTree();
             rightParseTree != null && rightParseTree.getRightParseTree() != null;
             rightParseTree = parseTree.getRightParseTree()) {

            final TokenType tmpOp = parseTree.getOperator();
            parseTree.setOperator(rightParseTree.getOperator());
            rightParseTree.setOperator(tmpOp);

            parseTree.setRightParseTree(rightParseTree.getRightParseTree());
            rightParseTree.setRightParseTree(rightParseTree.getLeftParseTree());

            final ParseTree tmpLeft = parseTree.getLeftParseTree();
            parseTree.setLeftParseTree(rightParseTree);
            rightParseTree.setLeftParseTree(tmpLeft);
        }
    }
}
