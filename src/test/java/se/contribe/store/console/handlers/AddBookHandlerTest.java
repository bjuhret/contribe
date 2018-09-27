package se.contribe.store.console.handlers;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import se.contribe.store.MockBookStore;
import se.contribe.store.console.MockCLIAdapter;
import se.contribe.store.console.MockConsoleController;
import se.contribe.store.pojos.Book;

/**
 *
 * @author bjuhr
 */
public class AddBookHandlerTest {

    private Book book;
    private Integer quantity;
    private String tell;
    private String yell;
        
    private void resetReceivedValues(){
        this.quantity = null;
        this.book = null;
        this.tell = null;
    }

    @Test
    public void testIntercept() {
        final MockBookStore store = new MockBookStore();
        final MockConsoleController controller = new MockConsoleController(store);
        final MockCLIAdapter adapter = controller.getAdapter();
        
        final Book book = store.list()[0];
        final Map<Integer,Book> booksInScope = new HashMap<>();
        booksInScope.put(0, book);
        controller.setBooksInScope(booksInScope);
        store.setAddConsumer((b,i) -> {this.book = b;this.quantity=i ;});
        adapter.registerAnswer("0", AddBookHandler.PLEASE_ENTER_BOOK_NUMBER);
        adapter.registerAnswer("1", AddBookHandler.PLEASE_ENTER_NUMBER_OF_COPIES);
        adapter.setTellConsumer(a -> this.tell = a);
        adapter.setYellConsumer(a -> this.yell = a);
        
        // Try to add the book once
        final ConsoleCommandHandler handler = new AddBookHandler();
        handler.intercept(adapter, controller, store);
        
        // Should work
        assertEquals(book, this.book);
        assertEquals(new Integer(1), this.quantity);
        assertEquals(AddBookHandler.BOOK_ADDED_MSG, this.tell);
        assertEquals(1, store.getBasket().size());
        
        resetReceivedValues();
        // Try with the same book again
        handler.intercept(adapter, controller, store);
        
        // Should fail this time
        assertEquals(book, this.book);
        assertEquals(new Integer(1), this.quantity);
        assertEquals(AddBookHandler.BOOK_NOT_ADDED_MSG, this.yell);
        assertEquals(1, store.getBasket().size());        
    }
    
}
