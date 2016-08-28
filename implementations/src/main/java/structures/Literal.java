package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Literal extends Parsable implements Executable {
    private int reValue;
    private int imValue;

    public Literal() {
    }

    public Literal(int reValue, int imValue) {
        this.reValue = reValue;
        this.imValue = imValue;
    }

    public int getReValue() {
        return reValue;
    }

    public void setReValue(int reValue) {
        this.reValue = reValue;
    }

    public int getImValue() {
        return imValue;
    }

    public void setImValue(int imValue) {
        this.imValue = imValue;
    }

    @Override
    public Type getType() {
        return Type.Literal;
    }

    @Override
    public void parse(final Parser parser) {
        boolean minused = false;
        if (parser.checkNextTokenType(TokenType.SUB)) {
            minused = true;
            parser.accept();
        } else if (parser.checkNextTokenType(TokenType.ADD))
            parser.accept();

        if (parser.checkNextTokenType(TokenType.RE_NUMBER)) {
            reValue = parseNumberValue(minused, parser);

            minused = false;
            if (parser.checkNextTokenType(TokenType.SUB, TokenType.ADD)) {
                if (parser.checkNextTokenType(TokenType.SUB))
                    minused = true;
                parser.accept();
                if (!parser.checkNextTokenType(TokenType.IM_NUMBER))
                    // to show error
                    parser.accept(TokenType.IM_NUMBER);
            }
            if (parser.checkNextTokenType(TokenType.IM_NUMBER))
                imValue = parseNumberValue(minused, parser);
        } else if (parser.checkNextTokenType(TokenType.IM_NUMBER)) {
            if (parser.checkNextTokenType(TokenType.IM_NUMBER))
                imValue = parseNumberValue(minused, parser);
        }
    }

    private int parseNumberValue(boolean minused, final Parser parser) {
        final Token numberToken = parser.accept(TokenType.RE_NUMBER, TokenType.IM_NUMBER);
        if (numberToken.getTokenType() == TokenType.RE_NUMBER)
            return Integer.valueOf(numberToken.getTokenString());
        int number = getImValue(numberToken.getTokenString());
        if (minused)
            return -number;
        else
            return number;
    }

    private int getImValue(final String number) {
        if (number.length() == 1)
            return 1;

        return Integer.valueOf(number.replace("j", ""));
    }

    public boolean isTrue() {
        return reValue != 0 && imValue != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        return reValue == literal.reValue && imValue == literal.imValue;
    }

    @Override
    public int hashCode() {
        int result = reValue;
        result = 31 * result + imValue;
        return result;
    }

    public static Literal add(Literal leftExpression, Literal rightExpression) {
        final Literal newLiteral = new Literal();

        newLiteral.setReValue(leftExpression.getReValue() + rightExpression.getReValue());
        newLiteral.setImValue(leftExpression.getImValue() + rightExpression.getImValue());

        return newLiteral;
    }

    public static Literal sub(Literal leftExpression, Literal rightExpression) {
        final Literal newLiteral = new Literal();

        newLiteral.setReValue(leftExpression.getReValue() - rightExpression.getReValue());
        newLiteral.setImValue(leftExpression.getImValue() - rightExpression.getImValue());

        return newLiteral;
    }

    public static Literal mul(Literal leftExpressionResult, Literal rightExpressionResult) {
        final Literal newLiteral = new Literal();

        int lRe = leftExpressionResult.getReValue();
        int lIm = leftExpressionResult.getImValue();
        int rRe = rightExpressionResult.getReValue();
        int rIm = rightExpressionResult.getImValue();
        newLiteral.setReValue(lRe * rRe - lIm * rIm);
        newLiteral.setImValue(lRe * rIm + rRe * lIm);

        return newLiteral;
    }

    public static Literal div(Literal leftExpressionResult, Literal rightExpressionResult) {
        final Literal newLiteral = new Literal();

        int lRe = leftExpressionResult.getReValue();
        int lIm = leftExpressionResult.getImValue();
        int rRe = rightExpressionResult.getReValue();
        int rIm = rightExpressionResult.getImValue();
        newLiteral.setReValue((lRe * rRe + lIm * rIm) / (rRe * rRe + rIm * rIm));
        newLiteral.setImValue((rRe * lIm - lRe * rIm) / (rRe * rRe + rIm * rIm));

        return newLiteral;
    }

    public static Literal mod(Literal leftExpressionResult, Literal rightExpressionResult) {
        final Literal newLiteral = new Literal();

        int lRe = leftExpressionResult.getReValue();
        int lIm = leftExpressionResult.getImValue();
        int rRe = rightExpressionResult.getReValue();
        int rIm = rightExpressionResult.getImValue();
        newLiteral.setReValue(lRe % rRe);
        newLiteral.setImValue(lIm % rIm);

        return newLiteral;
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        return this;
    }
}
