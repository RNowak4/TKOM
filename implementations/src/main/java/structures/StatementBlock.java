package structures;

import executor.Executable;
import executor.Executor;
import executor.Scope;
import parser.Parser;
import utils.Token;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;

public class StatementBlock extends Parsable implements Executable {
    private List<Parsable> instructions = new ArrayList<>();

    public List<Parsable> getInstructions() {
        return instructions;
    }

    public void addInstruction(final Parsable instruction) {
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
        while ((token = parser.checkAccept(TokenType.IF, TokenType.WHILE, TokenType.DEF, TokenType.ID, TokenType.RETURN, TokenType.SEMICOLON, TokenType.BRACKET_CLOSE, TokenType.PRINT)).getTokenType()
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

                case SEMICOLON: {
                    parser.accept();
                }
                break;

                case PRINT: {
                    final PrintStatement printStatement = new PrintStatement();
                    printStatement.parse(parser);
                    addInstruction(printStatement);
                }
                break;

                default:
                    throw new RuntimeException("You shouldn't see that message. Sth is broken.");
            }
        }
        parser.accept();
        log.trace("Successfully parsed statement block.");
    }

    @Override
    public Literal execute(Executor executor, Scope scope) {
        for (Parsable instruction : instructions) {
            final Executable executable = (Executable) instruction;
            final Literal result = executable.execute(executor, scope);
            if (scope.isReturn()) {
                return result;
            }
            if (instruction instanceof ReturnStatement) {
                scope.setReturn(true);
                return result;
            }
        }

        return null;
    }
}
