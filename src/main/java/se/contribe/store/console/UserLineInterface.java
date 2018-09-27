package se.contribe.store.console;

import java.util.Scanner;

/**
 * Provides abstraction from simple command line interfaces
 *
 * @author bjuhr
 */
public interface UserLineInterface {

    /**
     * Sends the question to the CLI and returns the result provided by the user
     * wrapped in a {@link Scanner}
     *
     * @param question question that should be presented to the user
     * @return the answer from the user wrapped in a scanner for ease of use
     */
    Scanner ask(String question);

    /**
     * Sends a statement to the user using normal tone
     *
     * @param statement statements to send
     */
    void tell(String statement);

    /**
     * Sends a statement to the user using a "raised" voice. This method is
     * normally used for informing the user about errors etc and its formatting
     * depends on the implementing classes
     *
     * @param statement statements to send
     */
    void yell(String statement);
}
