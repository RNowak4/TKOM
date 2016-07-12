package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

public class Literal extends Node {
    private int reValue;
    private int imValue;

    public Literal() {
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
}
