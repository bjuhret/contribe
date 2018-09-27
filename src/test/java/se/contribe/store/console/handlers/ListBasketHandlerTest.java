package se.contribe.store.console.handlers;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.contribe.store.MockBookStore;
import se.contribe.store.console.MockCLIAdapter;
import se.contribe.store.console.MockConsoleController;

/**
 *
 * @author bjuhr
 */
public class ListBasketHandlerTest {

    private static final String EXPECTED_LIST_OUTPUT = "0    How To Spend Money  Rich Bloke          1000000.00     1    ";

    private String output;
    
    public ListBasketHandlerTest() {
    }

    /**
     * Test of intercept method, of class ListBasketHandler.
     */
    @Test
    public void testIntercept() {
        final MockBookStore store = new MockBookStore();
        final MockConsoleController controller = new MockConsoleController(store);
        final MockCLIAdapter adapter = controller.getAdapter();
        
        
        store.add(store.list()[0], 1);
        
        adapter.setTellConsumer(a -> this.output = a);

        final ConsoleCommandHandler handler = new ListBasketHandler();
        handler.intercept(adapter, controller, store);
        assertEquals(EXPECTED_LIST_OUTPUT, this.output);
    }
    
}
