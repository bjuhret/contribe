package se.contribe.store.console.handlers;

import java.util.Optional;
import java.util.Scanner;
import se.contribe.store.BookList;
import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.pojos.Book;

/**
 * Handler for adding books to the shopping basket. Note that books cannot be
 * added if they have not first been displayed for the user via
 * {@link ListBooksHandler} or {@link SearchHandler} since the books position in
 * the lists will be used to avoid to much typing
 *
 * @author bjuhr
 */
public class AddBookHandler implements ConsoleCommandHandler {

    static final String BOOK_ADDED_MSG = "The book was added to basket";
    static final String BOOK_NOT_ADDED_MSG = "The book could not be added";
    static final String PLEASE_ENTER_BOOK_NUMBER = "Please enter book number";
    static final String NOT_A_VALID_BOOK_NUMBER = " is not a valid book number";
    static final String PLEASE_ENTER_NUMBER_OF_COPIES = "Please enter number of copies";
    static final String NOT_A_VALID_NUMBER = " is not a valid number";

    /**
     * Asks the user for the number of the book it would like to add
     *
     * @param console
     * @param controller
     * @param bookStore
     * @return
     */
    private Optional<Book> askForBook(UserLineInterface console, ConsoleController controller, BookStore bookStore) {
        Scanner bookAnswer = console.ask(PLEASE_ENTER_BOOK_NUMBER);
        if (!bookAnswer.hasNextInt()) {
            console.yell(bookAnswer.nextLine() + NOT_A_VALID_BOOK_NUMBER);
            intercept(console, controller, bookStore);
            return Optional.empty();
        }
        Book selectedBook = controller.getBooksInScope().get(bookAnswer.nextInt());
        return Optional.of(selectedBook);
    }

    /**
     * Asks the user how many of the previously selected book he/she would like
     * to add
     *
     * @param console
     * @param controller
     * @param bookStore
     * @return
     */
    private Optional<Integer> askForNumberOfBooks(UserLineInterface console, ConsoleController controller, BookStore bookStore) {
        Scanner copiesAnswer = console.ask(PLEASE_ENTER_NUMBER_OF_COPIES);
        if (!copiesAnswer.hasNextInt()) {
            console.yell(copiesAnswer.nextLine() + NOT_A_VALID_NUMBER);
            return Optional.empty();
        }
        return Optional.of(copiesAnswer.nextInt());
    }

    /**
     * This handler will keep on asking for a book to be added until the book
     * index and quantity is correct
     *
     * The user will after adding a book be prompted with if the book was added
     * or not. This follows the logic of the return values for {@link BookList#add(se.contribe.store.pojos.Book, int) }
     *
     * @param console
     * @param controller
     * @param bookStore
     */
    @Override
    public void intercept(UserLineInterface console, ConsoleController controller, BookStore bookStore) {

        Optional<Book> maybeBook = askForBook(console, controller, bookStore);
        if (!maybeBook.isPresent()) {
            intercept(console, controller, bookStore);
            return;
        }

        Optional<Integer> maybeNumberOfBooks = askForNumberOfBooks(console, controller, bookStore);
        if (!maybeNumberOfBooks.isPresent()) {
            intercept(console, controller, bookStore);
            return;
        }

        boolean added = bookStore.add(maybeBook.get(), maybeNumberOfBooks.get());
        if (added) {
            console.tell(BOOK_ADDED_MSG);
        } else {
            console.yell(BOOK_NOT_ADDED_MSG);
        }
    }

}
