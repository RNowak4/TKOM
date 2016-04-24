package utils;

import java.util.HashMap;
import java.util.Map;

public class PredefinedTokens {
    public static Map<String, TokenType> keywords = new HashMap<>();
    public static Map<String, TokenType> operators = new HashMap<>();

    static {
        keywords.put("function", TokenType.FUNCTION);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("def", TokenType.DEF);
        keywords.put("return", TokenType.RETURN);

        operators.put("<", TokenType.LOWER);
        operators.put("<=", TokenType.LOWER_EQUALS);
        operators.put(">", TokenType.GREATER);
        operators.put(">=", TokenType.GREATER_EQUALS);
        operators.put("(", TokenType.PARENTH_OPEN);
        operators.put("(", TokenType.PARENTH_CLOSE);
        operators.put("[", TokenType.SQUARE_BRACKET_OPEN);
        operators.put("]", TokenType.SQUARE_BRACKET_CLOSE);
        operators.put("{", TokenType.BRACKET_OPEN);
        operators.put("}", TokenType.BRACKET_CLOSE);
        operators.put("==", TokenType.EQUALS);
        operators.put("!=", TokenType.NOT_EQUALS);
        operators.put("+", TokenType.ADD);
        operators.put("-", TokenType.SUB);
        operators.put("*", TokenType.MUL);
        operators.put("/", TokenType.DIV);
        operators.put("%", TokenType.MODULO);
        operators.put(";", TokenType.COLON);
        operators.put(".", TokenType.DOT);
        operators.put(";", TokenType.SEMICOLON);
        operators.put("=", TokenType.ASSIGN);
    }
}
