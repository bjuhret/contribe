package se.contribe.store.console.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.console.util.Formatter;
import se.contribe.store.pojos.Book;

/**
 * Lists all book items currently in warehouse
 * @author bjuhr
 */
public class ListBooksHandler implements ConsoleCommandHandler {


    @Override
    public void intercept(UserLineInterface console, ConsoleController controller,BookStore bookStore) {
        
        String header = Formatter.centerTextInDelimiter("ALL BOOKS IN STORE");
        console.tell(header);
        
        Map<Integer,Book> bookList = new HashMap<>();
        for (int i = 0; i < bookStore.list().length; i++) {
            Book book = bookStore.list()[i];
            bookList.put(i, book);
            StringBuilder row = new StringBuilder(80)
                    .append(Formatter.fitToSize(5, Optional.of(Integer.toString(i))))
                    .append(book.formatted());
            console.tell(row.toString());

        }
        controller.setBooksInScope(bookList);
    }
    
}
