package se.contribe.store.console.command;

import java.util.HashMap;
import java.util.Map;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.ListBasketHandler;
import se.contribe.store.pojos.Book;

/** 
 * Lists all items in the basket
 * @author bjuhr
 */
public class ListBasketCommand implements ConsoleCommand{

    private static final String COMMAND = "basket";
    private static final String DESCRIPTION = "Lists the items in the basket";


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
        return new ListBasketHandler();
    }

    @Override
    public boolean displayCommand(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        return !basket.isEmpty();
    }
    
    
}
