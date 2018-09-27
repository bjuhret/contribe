package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.RemoveBookHandler;
import se.contribe.store.pojos.Book;

/**
 * Command for removing book(s) from the shopping basket
 * @author bjuhr
 */
public class RemoveBookCommand implements ConsoleCommand {

    private static final String COMMAND = "remove";
    private static final String DESCRIPTION = "Remove booksk from shopping basket";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }


    @Override
    public ConsoleCommandHandler conmmandHandler() {
        return new RemoveBookHandler();
    }

    @Override
    public boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        return !basket.isEmpty();
    }
}
