package se.contribe.store;

import java.math.BigDecimal;
import se.contribe.store.pojos.Book;

/**
 * Provides extended functionality for the {@link BookList} interface with
 * additional shop and shopping basket functionality
 *
 * @author bjuhr
 */
public interface ExtendedBookList extends BookList {

    /**
     * Removes a book or several books from the shopping basket if the book(s)
     * exists in the shopping basket. An attempt to remove book(s) from the
     * basket that does not exist in the basket will fail resulting in false as
     * return value from the method.
     *
     * The lookup against the shopping basket will done by comparing the books title,
     * author, and price as this will constitute the composite key for the book
     * item.
     *
     * @param book Book object that serves as reference for book(s) that should
     * be removed from the basket
     * @param quantity Number of books that should be removed from the shopping basket
     * @return true is book(s) could be successfully removed and false if book(s)
     * could not be removed according to the rules above
     */
    public boolean remove(Book book, int quantity);

    /**
     * Sums the items in the shopping basket and returns the total amount
     * @return amount rounded to two digits with rounding {@link BigDecimal#ROUND_HALF_UP}
     */
    public BigDecimal sumBasket();

    /**
     * Returns a list of all items in the book store
     * @return array of book items
     */
    public Book[] list();
}
