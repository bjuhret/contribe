package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.ListBooksHandler;
import se.contribe.store.pojos.Book;

/**
 * Lists all book items in book store
 * @author bjuhr
 */
public class ListBooksCommand implements ConsoleCommand{

    private static final String COMMAND = "list";
    private static final String DESCRIPTION = "List all available books";


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
        return new ListBooksHandler();
    }   

    @Override
    public boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        return true;
    }
}
