package se.contribe.store.console.handlers;

import se.contribe.store.BookStore;
import se.contribe.store.console.ConsoleController;
import se.contribe.store.console.UserLineInterface;

/**
 * Provides a common way to handle commands from a CLI environment
 *
 * @author bjuhr
 */
public interface ConsoleCommandHandler {

    /**
     * The controller ({@link ConsoleController}) will at this point delegate
     * control to the implementing handler. The handler will have access to the
     * context objects controller and bookStore and can handle the communication
     * with the user via an abstracted interface {@link UserLineInterface}
     *
     * When the handler are done it simply returns control to the controller by
     * exiting this method
     *
     * @param console abstracted interface enabling command line communication with user
     * @param controller context object
     * @param bookStore context object
     */
    void intercept(UserLineInterface console, ConsoleController controller, BookStore bookStore);

}
