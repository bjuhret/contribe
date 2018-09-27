package se.contribe.store.console;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;
import se.contribe.store.MockBookStore;
import se.contribe.store.console.command.ConsoleCommand;
import se.contribe.store.console.command.ListBooksCommand;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.ListBooksHandler;
import se.contribe.store.console.handlers.WelcomeHandler;
/**
 * 
 * @author bjuhr
 */
public class ConsoleControllerTest {

    private ConsoleCommandHandler resolvedHandler;
    private Set<ConsoleCommand> resolvedCommands;
    

    /**
     * Test of handleInput method, of class ConsoleController.
     */
    @Test
    public void testHandleInput() {
        
        final MockBookStore store = new MockBookStore();
        final MockConsoleController controller = new MockConsoleController(store);
        final MockCLIAdapter adapter = controller.getAdapter();
        
        MockConsoleController.setResolvedCommandHandlerInterceptor(h -> this.resolvedHandler = h);
        MockConsoleController.setResolvedCommands(rc -> this.resolvedCommands = rc);
        
        controller.handleInput(null);
        
        assertEquals(true, this.resolvedHandler != null);
        assertEquals(WelcomeHandler.class,this.resolvedHandler.getClass());
        assertTrue(this.resolvedCommands != null);
        assertTrue(this.resolvedCommands.size() > 0);

        this.resolvedHandler = null;
        controller.handleInput(new ListBooksCommand().command());
        
        assertEquals(true, this.resolvedHandler != null);
        assertEquals(ListBooksHandler.class,this.resolvedHandler.getClass());
        
    }
  
    
}
