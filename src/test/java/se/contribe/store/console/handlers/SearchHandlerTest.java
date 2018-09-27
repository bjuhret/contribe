package se.contribe.store.console.handlers;

import java.util.Iterator;
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
public class SearchHandlerTest {

    private static final String TITLE_TEST_1 = "Mastering åäö";
    private static final String AUTHOR_TEST_1 = "Cunning Bastard";
    
    /**
     * Test of intercept method, of class SearchHandler.
     */
    @Test
    public void testIntercept() {
        
        final MockBookStore store = new MockBookStore();
        final MockConsoleController controller = new MockConsoleController(store);
        final MockCLIAdapter adapter = controller.getAdapter();
        
        adapter.registerAnswer(TITLE_TEST_1, SearchHandler.ENTER_PHRASE_FOR_TITLE_OR_AUTHOR);

        final ConsoleCommandHandler handler = new SearchHandler();
        handler.intercept(adapter, controller, store);
        
        assertEquals(true, controller.getBooksInScope() != null);
        assertEquals(1, controller.getBooksInScope().size());
        final Book foundBook = controller.getBooksInScope().values().iterator().next();
        assertEquals(TITLE_TEST_1, foundBook.getTitle());
        
        adapter.registerAnswer(AUTHOR_TEST_1, SearchHandler.ENTER_PHRASE_FOR_TITLE_OR_AUTHOR);
        handler.intercept(adapter, controller, store);
        
        assertEquals(true, controller.getBooksInScope() != null);
        assertEquals(2, controller.getBooksInScope().size());
        final Iterator<Book> iterator = controller.getBooksInScope().values().iterator();
        final Book foundBook2_1 = iterator.next();
        final Book foundBook2_2 = iterator.next();
        assertEquals(AUTHOR_TEST_1, foundBook2_1.getAuthor());
        assertEquals(AUTHOR_TEST_1, foundBook2_2.getAuthor());
        assertEquals(false, foundBook2_1.equals(foundBook2_2));
    }
    
}
