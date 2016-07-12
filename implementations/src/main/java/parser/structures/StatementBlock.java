package parser.structures;

import parser.Parser;
import utils.Token;
import utils.TokenType;

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

    @Override
    public void parse(Parser parser) {
        log.trace("Parsing statement block.");
        parser.accept(TokenType.BRACKET_OPEN);
        Token token;
        while ((token = parser.checkAccept(TokenType.IF, TokenType.WHILE, TokenType.DEF, TokenType.ID, TokenType.RETURN)).getTokenType()
                != TokenType.BRACKET_CLOSE) {

            switch (token.getTokenType()) {
                case IF: {
                    final IfStatement ifStatement = new IfStatement();
                    ifStatement.parse(parser);
                    addInstruction(ifStatement);
                }
                break;

                case WHILE: {
                    final WhileStatement whileStatement = new WhileStatement();
                    whileStatement.parse(parser);
                    addInstruction(whileStatement);
                }
                break;

                case DEF: {
                    final InitStatement initStatement = new InitStatement();
                    initStatement.parse(parser);
                    addInstruction(initStatement);
                }
                break;

                case ID: {
                    addInstruction(parser.parseFunctionCallOrVariable());
                }
                break;

                case RETURN: {
                    final ReturnStatement returnStatement = new ReturnStatement();
                    returnStatement.parse(parser);
                    addInstruction(returnStatement);
                }
                break;

                default:
                    throw new RuntimeException("You shouldn't see that message. Sth is broken.");
            }
        }
        log.trace("Successfully parsed statement block.");
    }
}
