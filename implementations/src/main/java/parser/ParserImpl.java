package parser;

import lexer.Lexer;
import utils.Token;
import utils.TokenType;
import utils.structures.Function;
import utils.structures.Program;

import java.util.stream.Stream;

public class ParserImpl implements Parser {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ParserImpl.class);
    private Lexer lexer;

    public ParserImpl(Lexer lexer) {
        this.lexer = lexer;
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        return Stream.of(tokenTypes).anyMatch(t -> t.equals(token.getTokenType()));
    }

    @Override
    public Program parse() {
        throw new UnsupportedOperationException("TODO");
    }

    private Token accept(final TokenType... tokenTypes) {
        final Token nextToken = lexer.nextToken();
        if (isAcceptable(nextToken, tokenTypes)) {
            return nextToken;
        } else {
            StringBuilder sb = new StringBuilder()
                    .append("Unexptected token: ")
                    .append(nextToken.getTokenString())
                    .append(" of token type: ")
                    .append(nextToken.getTokenType())
                    .append(" in position: [")
                    .append(lexer.getLine())
                    .append(", ")
                    .append(lexer.getCharPosition())
                    .append("]. ")
                    .append("Expected one of the following: ");

            for (TokenType tokenType : tokenTypes) {
                sb
                        .append(tokenType)
                        .append(" ");
            }

            throw new RuntimeException(sb.toString());
        }
    }

    private Function parseFunction() {
        log.trace("Started parsing function.");
        final Function function = new Function();
//        function.setName();

        return function;
    }

}
