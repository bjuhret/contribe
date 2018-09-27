package se.contribe.store;

import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Test;
import se.contribe.store.pojos.Book;
import se.contribe.store.pojos.BookDetailed;

/**
 *
 * @author bjuhr
 */
public class BookStoreTest {

    final Book doesNotExist = new Book("On food and cooking", "Harold McGee", new BigDecimal(100));
    private final BookDetailed oneInStock = new BookDetailed("How To Spend Money", "Rich Bloke", new BigDecimal(1000000.0000), 1);
    private final BookDetailed noneInStock = new BookDetailed("Desired", "Rich Bloke", new BigDecimal(564.5000), 0);
    private final BookDetailed fiveInStock = new BookDetailed("Generic Title", "First Author", new BigDecimal(185.5000), 5);
    private final BookDetailed threeInStock = new BookDetailed("Random Sales", "Cunning Bastard", new BigDecimal(499.5000), 3);
    private final BookDetailed twentyInStock = new BookDetailed("Random Sales", "Cunning Bastard", new BigDecimal(999.0000), 20);
    private final BookDetailed anotherThreeInStock = new BookDetailed("Generic Title", "Second Author", new BigDecimal(1748.0000), 3);
    private final BookDetailed fifteenInStock = new BookDetailed("Mastering åäö", "Average Swede", new BigDecimal(762.0000), 15);
    
    BookDetailed[] allBooks = new BookDetailed[]{
        oneInStock,noneInStock,fiveInStock,threeInStock,twentyInStock,anotherThreeInStock,fifteenInStock
    };

    /**
     * Test of list method, of class BookStore.
     */
    @Test
    public void testList_0args() {
        BookStore bookStore = new MockBookStore();
        Book[] expResult = this.allBooks;
        Book[] result = bookStore.list();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of list method, of class BookStore.
     */
    @Test
    public void testList_String() {
        String searchString = "Random Sales";
        BookStore instance = new MockBookStore();
        Book[] expResult = new BookDetailed[]{threeInStock,twentyInStock};
        Book[] result = instance.list(searchString);
        assertArrayEquals(expResult, result);        
    }

    /**
     * Test of sumBasket method, of class BookStore.
     */
    @Test
    public void testDoPurchase() {
        BookStore store = new MockBookStore();
        store.add(oneInStock, 1);
        store.add(threeInStock, 2);
        store.add(fiveInStock, 2);
        store.remove(threeInStock, 1);
        BigDecimal expected = new BigDecimal(1000870.5).setScale(2);
        BigDecimal actual = store.sumBasket();
        assertEquals(expected, actual);
    }


    /**
     * Test of add method, of class BookStore.
     */
    @Test
    public void testAdd() {

        BookStore store = new MockBookStore();
        
        assertEquals(true, store.add(this.oneInStock, 1));
        assertEquals(false, store.add(this.oneInStock, 1));
        assertEquals(false, store.add(this.noneInStock, 1));
        assertEquals(false, store.add(doesNotExist, 1));
        assertEquals(true, store.add(this.threeInStock, 3));
        assertEquals(false, store.add(this.fifteenInStock, 16));
        
    }

    /**
     * Test of buy method, of class BookStore.
     */
    @Test
    public void testBuy() {
        BookStore store = new MockBookStore();
        int[] actual = store.buy(this.oneInStock,this.oneInStock,this.noneInStock,doesNotExist);
        int[] expected = new int[]{0,1,1,2};
        assertArrayEquals(expected, actual);
    }

    /**
     * Test of remove method, of class BookStore.
     */
    @Test
    public void testRemove() {
        
        final BookStore store1 = new MockBookStore();
        store1.add(oneInStock, 1);
        assertTrue(store1.remove(oneInStock, 1));
        assertFalse(store1.remove(oneInStock, 1));
        
        final BookStore store2 = new MockBookStore();
        store2.add(threeInStock, 2);
        assertFalse(store2.remove(threeInStock, 3));
        
        final BookStore store3 = new MockBookStore();        
        assertFalse(store3.remove(oneInStock, 1));
        
    }

}
