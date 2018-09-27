/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.contribe.store.console;

import com.sun.corba.se.impl.activation.CommandHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.contribe.store.BookStore;
import se.contribe.store.console.command.AddBookCommand;
import se.contribe.store.console.command.ConsoleCommand;
import se.contribe.store.console.command.ListBasketCommand;
import se.contribe.store.console.command.ListBooksCommand;
import se.contribe.store.console.command.RemoveBookCommand;
import se.contribe.store.console.command.SearchCommand;
import se.contribe.store.console.handlers.ConsoleCommandHandler;
import se.contribe.store.console.handlers.WelcomeHandler;
import se.contribe.store.console.util.Formatter;
import se.contribe.store.pojos.Book;

/**
 * Provides controller functionality for command line interfaces.
 *
 * The controllers main responsibility is to listen for commands and find the
 * right command handler that can then take over the communication with the
 * user.
 *
 * The controller is also responsible for displaying additional available
 * commands that will depend on the context and history of previously executed
 * commands
 *
 * @author bjuhr
 */
public class ConsoleController {

    private static final String ERR_COMMAND_HAND_CNT = "There may only be on command handler for each state. Actual handlers found is: ";

    private Map<Integer, Book> booksInScope = new HashMap<>();
    private final BookStore bookStore;
    private UserLineInterface cli = new ConsoleAdapter();

    // Interceptor for testing 
    static Consumer<ConsoleCommandHandler> resolvedCommandHandlerInterceptor;
    // Interceptor for testing 
    static Consumer<Set<ConsoleCommand>> resolvedCommandsInterceptor;

    public ConsoleController(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    /**
     * All commands that should be handled by this controller needs to be added
     * here. Order is of no importance
     */
    private static final ConsoleCommand[] commands = new ConsoleCommand[]{
        new ListBooksCommand(),
        new SearchCommand(),
        new AddBookCommand(),
        new ListBasketCommand(),
        new RemoveBookCommand()
    };

    private static Stream<ConsoleCommand> commandsAsStream() {
        return Arrays.stream(commands);
    }

    /**
     * Finds the {@link CommandHandler} that matches the commands received from
     * the user. If no match can be found for the user command the default
     * {@link WelcomeHandler} handler will be used
     *
     * @param command text command from user
     * @return matched command handler (see method description)
     */
    private static ConsoleCommandHandler resolveCommandHandler(String command) {
        ConsoleCommandHandler handler = commandsAsStream()
                .filter(c -> c.command().equals(command))
                .map(cc -> cc.conmmandHandler())
                .findAny().orElse(new WelcomeHandler());
        if (resolvedCommandHandlerInterceptor != null) {
            resolvedCommandHandlerInterceptor.accept(handler);
        }
        return handler;
    }

    /**
     * Dynamically resolves which commands that should be avaliable depending on
     * the state of the shopping basket and book store.
     *
     * If a command should be displayed or not is delegated to the
     * {@link ConsoleCommand}
     *
     * @param basket current shopping basket
     * @param booksInScope a list of all books that was displayed for the user
     * last
     * @return a set of commands matching a predefined context
     */
    private static Set<ConsoleCommand> resolveCommands(HashMap<Book, Integer> basket, Map<Integer, Book> booksInScope) {
        Set<ConsoleCommand> commands = commandsAsStream()
                .filter(c -> c.displayCommand(basket, booksInScope))
                .collect(Collectors.toSet());
        if (resolvedCommandsInterceptor != null) {
            resolvedCommandsInterceptor.accept(commands);
        }
        return commands;
    }

    public void setBooksInScope(Map<Integer, Book> booksInScope) {
        this.booksInScope = booksInScope;
    }

    public Map<Integer, Book> getBooksInScope() {
        return booksInScope;
    }

    /**
     * Returns the shopping basket sorted by title, author, and prince in
     * natural order
     *
     * @return
     */
    public SortedMap<Book, Integer> getSortedBasket() {
        return new TreeMap(bookStore.getBasket());
    }

    protected UserLineInterface getInterfaceAdapter() {
        return this.cli;
    }

    /**
     * Main entry point method where the controller find the correct handler and
     * delegates the user interaction to the handler
     *
     * @param command text command sent by user
     */
    public void handleInput(String command) {

        ConsoleCommandHandler handler = resolveCommandHandler(command);
        handler.intercept(getInterfaceAdapter(), this, bookStore);
        printCommands();
    }

    /**
     * Displays all context bound commands for user
     */
    private void printCommands() {

        getInterfaceAdapter().tell(Formatter.centerTextInDelimiter("AVAILABLE COMMANDS"));
        getInterfaceAdapter().tell(Formatter.getDelimiterLine());

        resolveCommands(this.bookStore.getBasket(), this.getBooksInScope())
                .forEach(c -> getInterfaceAdapter().tell(c.format()));

        getInterfaceAdapter().tell(Formatter.getDelimiterLine());
    }

}
