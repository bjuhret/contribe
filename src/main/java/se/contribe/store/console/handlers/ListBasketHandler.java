package se.contribe.store.console.handlers;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.console.util.Formatter;
import se.contribe.store.pojos.Book;

/**
 * Lists all items in shopping basket
 * @author bjuhr
 */
public class ListBasketHandler implements ConsoleCommandHandler {


    @Override
    public void intercept(UserLineInterface console, ConsoleController controller,BookStore bookStore) {
        
        String header = Formatter.centerTextInDelimiter("BOOKS IN BASKET");
        console.tell(header);
        
        SortedMap<Book, Integer> orderedBasket = controller.getSortedBasket();
        int cnt = 0;
        for (Map.Entry<Book, Integer> entry : orderedBasket.entrySet()) {
            StringBuilder row = new StringBuilder(80)
                    .append(Formatter.fitToSize(5, Optional.of(Integer.toString(cnt))))
                    .append(entry.getKey().formatted())
                    .append(Formatter.fitToSize(5, Optional.of(Integer.toString(entry.getValue()))));
            console.tell(row.toString());
        }
        controller.setBooksInScope(Collections.emptyMap());
    }

    
}
