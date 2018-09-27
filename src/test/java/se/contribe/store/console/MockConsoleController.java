package se.contribe.store.console;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import se.contribe.store.BookStore;
import se.contribe.store.console.command.ConsoleCommand;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.pojos.Book;

/**
 *
 * @author bjuhr
 */
public class MockConsoleController extends ConsoleController{
    
    private Consumer<Map<Integer, Book>> booksInScopeConsumer;
    private MockCLIAdapter adapter = new MockCLIAdapter();
    
    public MockConsoleController(BookStore bookStore) {
        super(bookStore);
    }

    public MockCLIAdapter getAdapter() {
        return adapter;
    }
    
    
    public static void setResolvedCommandHandlerInterceptor(Consumer<ConsoleCommandHandler> consumer){
        ConsoleController.resolvedCommandHandlerInterceptor = consumer;
    }
    
    public static void setResolvedCommands(Consumer<Set<ConsoleCommand>> consumer){
        ConsoleController.resolvedCommandsInterceptor = consumer;
    }    
    
    

    @Override
    public void setBooksInScope(Map<Integer, Book> booksInScope) {
        if (booksInScopeConsumer != null) {
            booksInScopeConsumer.accept(booksInScope);
        }
        super.setBooksInScope(booksInScope); 
    }

    public void setBooksInScopeConsumer(Consumer<Map<Integer, Book>> booksInScopeConsumer) {
        this.booksInScopeConsumer = booksInScopeConsumer;
    }

    @Override
    protected UserLineInterface getInterfaceAdapter() {
        return adapter;
    }
    
    
    
}
