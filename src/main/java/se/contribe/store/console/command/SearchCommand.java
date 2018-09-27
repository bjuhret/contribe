package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.SearchHandler;
import se.contribe.store.pojos.Book;

/**
 * Command for searching books authors or book titles
 * @author bjuhr
 */
public class SearchCommand implements ConsoleCommand{

    private static final String COMMAND = "search";
    private static final String DESCRIPTION = "To search title or author";

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
        return new SearchHandler();
    }    

    @Override
    public boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        return true;
    }
    
}
