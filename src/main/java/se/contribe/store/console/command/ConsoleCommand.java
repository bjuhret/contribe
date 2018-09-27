package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.util.Formatter;
import se.contribe.store.pojos.Book;

/**
 * Defines the common interface for command tailored for CLI interaction
 *
 * @author bjuhr
 */
public interface ConsoleCommand {

    /**
     * True if command should be displayed which will be based on the context
     * supplied by the parameters, else false
     *
     * @param basket current shopping basket
     * @param booksInScope list of books last displayed for the user
     * @return true is the command should be displayed
     */
    boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope);

    /**
     * The string that the used input should be matched against
     *
     * @return
     */
    String command();

    /**
     * A longer description of the command
     *
     * @return
     */
    String description();

    /**
     * The associated handler that shall be designed to handle the command and
     * take over control from the controller
     *
     * @return
     */
    ConsoleCommandHandler conmmandHandler();

    /**
     * Formats the command and command description 
     * @return 
     */
    default String format() {
        return "- " + Formatter.fitToSize(20, Optional.of(command()))
                + Formatter.fitToSize(50, Optional.of("=> " + description()));
    }
}
