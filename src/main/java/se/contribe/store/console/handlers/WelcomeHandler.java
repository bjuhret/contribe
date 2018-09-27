package se.contribe.store.console.handlers;

import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;
import se.contribe.store.console.util.Formatter;

/**
 * The default handler that runs if no other command can be interpreted and just
 * says welcome
 *
 * @author bjuhr
 */
public class WelcomeHandler implements ConsoleCommandHandler {

    @Override
    public void intercept(UserLineInterface console, ConsoleController controller, BookStore bookStore) {

        console.tell(Formatter.getDelimiterLine());
        console.tell(Formatter.centerTextInDelimiter("WELCOME TO THE BOOK STORE"));
        console.tell(Formatter.getDelimiterLine());
    }

}
