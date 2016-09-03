package structures;

import utils.TokenType;

public abstract class Expression extends ParseTree {
    protected Expression leftExpression;
    protected Expression rightExpression;
    protected TokenType operator = TokenType.UNDEFINED;

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    protected ParseTree getLeftParseTree() {
        return leftExpression;
    }

    @Override
    protected ParseTree getRightParseTree() {
        return rightExpression;
    }

    @Override
    protected void setLeftParseTree(ParseTree parseTree) {
        this.leftExpression = (Expression) parseTree;
    }

    @Override
    public TokenType getOperator() {
        return operator;
    }

    @Override
    protected void setOperator(TokenType operator) {
        this.operator = operator;
    }

    @Override
    protected void setRightParseTree(ParseTree parseTree) {
        this.rightExpression = (Expression) parseTree;
    }
}
