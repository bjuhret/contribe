package se.contribe.store;

import se.contribe.store.pojos.Book;

/**
 * Provides methods for displaying information from the book store as well as
 * adding books to a shopping basket
 *
 * @author bjuhr
 */
public interface BookList {

    /**
     * Provides a list of books where either the author name or book title
     * matches the provided search phrase. The matching will ignore case
     *
     * @param searchString String used as search term
     * @return list of books as result of the search
     */
    public Book[] list(String searchString);

    /**
     * Adds a book or several books to the shopping basket if the book exists in
     * stock. A check against the warehouse will be done for the quantity given
     * so two as quantity will fail even if there are one item in stock.
     *
     * The lookup against the warehouse will done by comparing the books title,
     * author, and price as this will constitute the composite key for the book
     * item.
     *
     * @param book Book object that serves as reference for book(s) that should
     * be added to basket
     * @param quantity Number of books that should be added to shopping basket
     * @return true is book(s) could be successfully added and false if book(s)
     * could not be added according to the rules above
     */
    public boolean add(Book book, int quantity);

    /**
     * Provides warehouse feedback for the books sent in as parameters. The
     * resulting array will mirror the warehouse status with the following
     * possible return values:
     *
     * <ul>
     * <li>0: The book exists in stock</li>
     * <li>1: The book is not in stock</li>
     * <li>2: The book does not exist</li>
     * </ul>
     *
     * The return values position in the array will mirror the books varargs
     * position so the first books information will be found at index 0.
     *
     * Please note that the warehouse check is done for all the books supplied
     * as parameters which means that the same book can give both the status 0
     * but also 1 if the warehouse only houses one book.
     *
     * Please also note that this do not add the books in the shopping basket
     * since the intent of the method is to test if all books can be bought.
     * Please use {@link ExtendedBookList#remove(se.contribe.store.pojos.Book, int)}, {@link BookList#add(se.contribe.store.pojos.Book, int)}, and {@link ExtendedBookList#commitBasket()} for this functionality
     *
     * @param books a number of books that should be verified against the warehouse
     * @return array of warehouse stock verification notices (see method comment)
     */
    public int[] buy(Book... books);

}
