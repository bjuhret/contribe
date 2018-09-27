package se.contribe.store.console.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.console.util.Formatter;
import se.contribe.store.pojos.Book;

/**
 * Provides search capabilities for title or author name searches
 *
 * @author bjuhr
 */
public class SearchHandler implements ConsoleCommandHandler {

    static final String ENTER_PHRASE_FOR_TITLE_OR_AUTHOR = "Enter phrase for title or author";
    private static final String NO_MATCHES_PLEASE_TRY_AGAIN = "No matches please try again";
    private static final String SEARCH_RESULTS = "SEARCH RESULTS";

    @Override
    public void intercept(UserLineInterface console, ConsoleController controller, BookStore bookStore) {

        Scanner answer = console.ask(ENTER_PHRASE_FOR_TITLE_OR_AUTHOR);
        Book[] results = bookStore.list(answer.nextLine());

        if (results.length == 0) {
            console.tell(NO_MATCHES_PLEASE_TRY_AGAIN);
            controller.setBooksInScope(Collections.emptyMap());
            intercept(console, controller, bookStore);
            return;
        }

        Map<Integer, Book> bookList = new HashMap<>();
        console.tell(Formatter.centerTextInDelimiter(SEARCH_RESULTS));
        for (int i = 0; i < results.length; i++) {
            Book book = results[i];
            bookList.put(i, book);
            StringBuilder row = new StringBuilder(80)
                    .append(Formatter.fitToSize(5, Optional.of(Integer.toString(i))))
                    .append(book.formatted());
            console.tell(row.toString());
        }
        controller.setBooksInScope(bookList);
    }

}
