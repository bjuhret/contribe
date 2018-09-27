package se.contribe.store.console.handlers;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.contribe.store.MockBookStore;
import se.contribe.store.console.MockCLIAdapter;
import se.contribe.store.console.MockConsoleController;
import se.contribe.store.pojos.Book;

/**
 *
 * @author bjuhr
 */
public class RemoveBookHandlerTest {
    
    private Book book;
    private Integer quantity;
    private String tell;
    private String yell;
        

    /**
     * Test of intercept method, of class RemoveBookHandler.
     */
    @Test
    public void testIntercept() {
        final MockBookStore store = new MockBookStore();
        final MockConsoleController controller = new MockConsoleController(store);
        final MockCLIAdapter adapter = controller.getAdapter();
        
        final Book book = store.list()[0];

        store.setRemoveConsumer((b,i) -> {this.book = b;this.quantity=i ;});
        adapter.registerAnswer("0", RemoveBookHandler.PLEASE_ENTER_BOOK_NUMBER);
        adapter.registerAnswer("1", RemoveBookHandler.PLEASE_ENTER_NUMBER_OF_COPIES_TO_REMOVE);
        adapter.setTellConsumer(a -> this.tell = a);
        adapter.setYellConsumer(a -> this.yell = a);
        
        store.add(book, 1);
        // Try to remove the book
        final ConsoleCommandHandler handler = new RemoveBookHandler();
        handler.intercept(adapter, controller, store);
        
        assertEquals(book, this.book);
        assertEquals(new Integer(1), this.quantity);
        assertEquals(RemoveBookHandler.THE_BOOKS_WAS_REMOVED_FROM_BASKET, this.tell);
        assertEquals(0, store.getBasket().size());
        
    }
    
}
