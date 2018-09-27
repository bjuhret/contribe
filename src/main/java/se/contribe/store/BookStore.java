package se.contribe.store;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import static se.contribe.store.BookStockStatus.*;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.data.URLBookListReader;
import se.contribe.store.pojos.Book;
import se.contribe.store.pojos.BookDetailed;

/**
 * Provides book store search, list and shopping basket functionality.
 *
 * @author bjuhr
 */
public class BookStore implements ExtendedBookList {

    private final HashMap<Book, Integer> basket = new HashMap();

    @Override
    public Book[] list() {
        return getAllBooks()
                .stream()
                .map(b -> (Book) b)
                .toArray(Book[]::new);
    }

    @Override
    public Book[] list(String searchString) {
        return getAllBooks()
                .stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(searchString) || b.getTitle().equalsIgnoreCase(searchString))
                .map(b -> (Book) b)
                .toArray(Book[]::new);
    }

    /**
     * Returns the shopping basket that is structured as having a book as key
     * and number of books as value
     *
     * @return map of books to quantity
     */
    public HashMap<Book, Integer> getBasket() {
        return this.basket;
    }

    /**
     * True is shopping basket is empty
     *
     * @return
     */
    public boolean isBasketEmpty() {
        return this.basket.isEmpty();
    }

    @Override
    public BigDecimal sumBasket() {
        BigDecimal amount = basket
                .entrySet()
                .stream()
                .map(es -> es.getKey().getPrice().multiply(BigDecimal.valueOf(es.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.setScale(2);
    }

    /**
     * Returns all books with inventory status {@link BookDetailed} thats exists
     * as inventory items
     *
     * @return
     */
    protected Set<BookDetailed> getAllBooks() {
        return new URLBookListReader().getBooks();
    }

    @Override
    public boolean add(Book book, int quantity) {

        final int newTotalQuantity = basket.containsKey(book) ? basket.get(book) + quantity : quantity;

        final BookStockStatus purchaseValidity = getPurchaseValidity(book, newTotalQuantity, getAllBooks());
        if (purchaseValidity == OK) {
            basket.put(book, newTotalQuantity);
            return true;
        }
        return false;
    }

    /**
     * Takes a book together with its quantity and checks this against the
     * warehouse status to determine the stock status if seen as if this was a
     * purchase.
     *
     * Not that the status will be calculated as a transaction meaning that if
     * the method is asked for two books and there are only one in stock
     * {@link BookStockStatus#NOT_IN_STOCK} will be returned
     *
     * @param book the book to check the stock for
     * @param quantity number of books that should be checked for
     * @param allBooks a collection of all books in stock with inventory
     * statuses
     * @return Validity of tested purchase (see comments above)
     */
    private BookStockStatus getPurchaseValidity(Book book, int quantity, Set<BookDetailed> allBooks) {
        return allBooks
                .stream()
                .filter(b -> isSameBook(b, book))
                .findAny()
                .map(b -> b.getItemsInStock() >= quantity ? OK : NOT_IN_STOCK)
                .orElse(DOES_NOT_EXIST);
    }

    /**
     * Checks to see if two books are the same taking into consideration that
     * one or both of the books could be a sub classed book as
     * {@link BookDetailed}
     *
     * @param thisBook first book to compare
     * @param thatBook second book to compare
     * @return true if the two books have the same title, author and price
     */
    private boolean isSameBook(Book thisBook, Book thatBook) {
        return thisBook.getTitle().equals(thatBook.getTitle())
                && thisBook.getAuthor().equals(thatBook.getAuthor())
                && thisBook.getPrice().equals(thatBook.getPrice());
    }

    @Override
    public int[] buy(Book... books) {

        final Set<BookDetailed> allBooks = getAllBooks();
        final Map<Book, Integer> bookCounter = new HashMap<>();
        int[] statuses = new int[books.length];
        for (int i = 0; i < books.length; i++) {
            Book book = books[i];
            Integer updatedCount = bookCounter.get(book) == null ? 1 : bookCounter.get(book) + 1;
            bookCounter.putIfAbsent(book, updatedCount);
            int status = getPurchaseValidity(book, updatedCount, allBooks).asNumberCode();
            statuses[i] = status;
        }

        return statuses;
    }

    @Override
    public boolean remove(Book book, int quantity) {

        boolean isInBasket = basket.containsKey(book) && basket.get(book) >= quantity;

        if (isInBasket) {
            final Integer newTotal = basket.get(book) - quantity;

            if (newTotal > 0) {
                basket.put(book, newTotal);
            } else {
                basket.remove(book);
            }
            return true;
        }
        return false;
    }

    /**
     * Main class in jar used for CLI access providing some basic shop as well
     * as shopping basket functionality over the command line
     *
     * @param args Arguments to CLI
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ConsoleController controller = new ConsoleController(new BookStore());
        controller.handleInput(null);
        while (true) {
            if (scanner.hasNextLine()) {
                controller.handleInput(scanner.nextLine());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // Just move on and pretend that nothing happened 
            }
        }

    }

}
