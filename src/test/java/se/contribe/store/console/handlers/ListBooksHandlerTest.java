package se.contribe.store.console.handlers;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import se.contribe.store.MockBookStore;
import se.contribe.store.console.MockConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.pojos.Book;

/**
 *
 * @author bjuhr
 */
public class ListBooksHandlerTest {
    
    private Book[] receivedBooks;
    private Map<Integer, Book> booksInScope;
    
    private void setReceivedBooks(Book[] books){
        this.receivedBooks = books;
    }
    
    private void setBooksInScope(Map<Integer, Book> booksInScope) {
        this.booksInScope = booksInScope;
    }
    
    /**
     * Test of intercept method, of class ListBooksHandler.
     */
    @Test
    public void testIntercept() {
        MockBookStore store = new MockBookStore();
        MockConsoleController controller = new MockConsoleController(store);
        UserLineInterface adapter = controller.getAdapter();
        
        store.setListConsumer(l -> setReceivedBooks(l));
        controller.setBooksInScopeConsumer(m -> setBooksInScope(m));

        ListBooksHandler instance = new ListBooksHandler();
        instance.intercept(adapter, controller, store);
        
        final Book[] listFromStore = store.list();
        
        // The original list shoudl have the same size as the received collection
        final boolean isListsSameSize = listFromStore.length == booksInScope.values().size();
        // Merge as set to get union of received from book store and set as in context by handler
        // needed to be able to see that we got the same elements
        int mergedSize = Stream.concat(Arrays.stream(listFromStore),booksInScope.values().stream())
                .collect(Collectors.toSet()).size();
        final boolean isMergedSameSize = mergedSize == listFromStore.length;
        
        // All three variants hasve the same size, we are happy
        Assert.assertTrue(isListsSameSize && isMergedSameSize);
        
    }


 
    
}
