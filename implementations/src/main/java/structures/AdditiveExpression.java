package structures;

import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class AdditiveExpression extends ParseTree {
    protected ParseTree leftParseTree = new MultiplicativeExpression();
    protected ParseTree rightParseTree;
    protected TokenType operator = TokenType.UNDEFINED;

    public ParseTree getLeftParseTree() {
        return leftParseTree;
    }

    public ParseTree getRightParseTree() {
        return rightParseTree;
    }

    public void setLeftParseTree(ParseTree parseTree) {
        this.leftParseTree = parseTree;
    }

    @Override
    public TokenType getOperator() {
        return operator;
    }

    @Override
    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public void setRightParseTree(ParseTree parseTree) {
        this.rightParseTree = parseTree;
    }

    @Override
    public Type getType() {
        return Type.Expression;
    }

    @Override
    public void parse(final Parser parser) {
        leftParseTree.parse(parser);
        if (parser.checkNextTokenType(TokenType.ADD, TokenType.SUB)) {
            final Token token = parser.accept();
            operator = token.getTokenType();
            rightParseTree = new AdditiveExpression();
            rightParseTree.parse(parser);
        }

        normalize(this);
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
//        normalize(this);
        final Literal leftExpressionResult = leftParseTree.execute(executor, scope);

        if (rightParseTree == null) {
            return leftExpressionResult;
        } else {
            final Literal rightExpressionResult = rightParseTree.execute(executor, scope);

            if (operator == TokenType.ADD)
                return Literal.add(leftExpressionResult, rightExpressionResult);
            else if (operator == TokenType.SUB)
                return Literal.sub(leftExpressionResult, rightExpressionResult);
            else
                throw new RuntimeException("Bad operator!");
        }
    }
}
