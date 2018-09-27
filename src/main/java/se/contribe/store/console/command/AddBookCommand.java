package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import se.contribe.store.console.handlers.AddBookHandler;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.pojos.Book;

/**
 * Command for adding a book to the shopping basket
 * @author bjuhr
 */
public class AddBookCommand implements ConsoleCommand{

    private static final String COMMAND = "add";
    private static final String DESCRIPTION = "Adds a book to the shopping basket";


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
        return new AddBookHandler();
    }

    @Override
    public boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        return !booksInScope.isEmpty();
    }
    
}
