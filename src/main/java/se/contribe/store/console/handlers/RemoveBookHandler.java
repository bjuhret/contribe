package se.contribe.store.console.handlers;

import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import se.contribe.store.BookStore;
import se.contribe.store.ExtendedBookList;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.pojos.Book;

/**
 * Handler for removing books from the shopping basket.
 *
 * @author bjuhr
 */
public class RemoveBookHandler implements ConsoleCommandHandler {

    static final String CANNOT_FIND_BOOK_TO_REMOVE = "Cannot find book to remove";
    static final String THE_COPIES_ARGUMENT_IS_NOT_VALID = "The copies argument is not valid";
    static final String THE_BOOKS_WAS_REMOVED_FROM_BASKET = "The book(s) was removed from basket";
    static final String THE_BOOKS_COULD_NOT_BE_REMOVED = "The book(s) could not be removed";
    static final String PLEASE_ENTER_BOOK_NUMBER = "Please enter book number";
    static final String PLEASE_ENTER_NUMBER_OF_COPIES_TO_REMOVE = "Please enter number of copies to remove";

    /**
     * Looks up the book from the basket by its displayed index
     * @param controller
     * @param index
     * @return 
     */
    private Optional<Book> getBookInBasketByIndex(ConsoleController controller, int index) {
        Iterator<Book> iterator = controller.getSortedBasket().keySet().iterator();
        int cnt = 0;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (cnt == index) {
                return Optional.of(book);
            }
            cnt++;
        }
        return Optional.empty();
    }

    /**
     * ASk the user what it is that he would like to remove
     * @param console
     * @param controller
     * @param bookStore
     * @return 
     */
    private Optional<Book> askForBook(UserLineInterface console, ConsoleController controller, BookStore bookStore) {
        Scanner answer = console.ask(PLEASE_ENTER_BOOK_NUMBER);
        if (!answer.hasNextInt()) {
            return Optional.empty();
        }
        return getBookInBasketByIndex(controller, answer.nextInt());
    }

    /**
     * Ask the user how many copies he would like to remove
     * @param console
     * @param controller
     * @param bookStore
     * @return 
     */
    private Optional<Integer> askForCopies(UserLineInterface console, ConsoleController controller, BookStore bookStore) {
        Scanner answer = console.ask(PLEASE_ENTER_NUMBER_OF_COPIES_TO_REMOVE);
        if (!answer.hasNextInt()) {
            return Optional.empty();
        }
        return Optional.of(answer.nextInt());

    }
    
    
    /**
     * This handler will keep on asking for a book to be removed until the book
     * index and quantity is correct
     *
     * The user will after removing a book be prompted with if the book was removed
     * or not. This follows the logic of the return values for {@link ExtendedBookList#remove(se.contribe.store.pojos.Book, int) }
     *
     * @param console
     * @param controller
     * @param bookStore
     */
    @Override
    public void intercept(UserLineInterface console, ConsoleController controller, BookStore bookStore) {

        Optional<Book> maybeBook = askForBook(console, controller, bookStore);
        if (!maybeBook.isPresent()) {
            console.yell(CANNOT_FIND_BOOK_TO_REMOVE);
            intercept(console, controller, bookStore);
            return;
        }

        Optional<Integer> maybeCopies = askForCopies(console, controller, bookStore);
        if (!maybeCopies.isPresent()) {
            console.yell(THE_COPIES_ARGUMENT_IS_NOT_VALID);
            intercept(console, controller, bookStore);
            return;
        }

        boolean removed = bookStore.remove(maybeBook.get(), maybeCopies.get());
        if (removed) {
            console.tell(THE_BOOKS_WAS_REMOVED_FROM_BASKET);
        } else {
            console.tell(THE_BOOKS_COULD_NOT_BE_REMOVED);
        }
    }

}
