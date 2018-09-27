package se.contribe.store.console.util;

import java.util.Collections;
import java.util.Optional;

/**
 * Provides common String formatter functionality
 *
 * @author bjuhr
 */
public class Formatter {

    private static final String LINE_DELIMITER_CHARACTER = "-";
    private static final int COLUMN_WIDTH = 80;

    /**
     * Takes a string trims it down to the specified size
     *
     * @param size
     * @param value
     * @return
     */
    private static String trimToSize(int size, String value) {
        if (value.length() <= size) {
            return value;
        } else {
            return value.substring(0, size);
        }
    }

    /**
     * Takes a string and centers it in a sequence of delimiter characters
     * {@link Formatter#LINE_DELIMITER_CHARACTER}
     *
     * @param text
     * @return
     */
    public static String centerTextInDelimiter(String text) {
        int offSet = 2;
        int leftFill = (COLUMN_WIDTH - text.length() - offSet) / 2;
        int rightFill = COLUMN_WIDTH - leftFill - text.length() - offSet;
        return new StringBuilder(80)
                .append(repeatString(leftFill, LINE_DELIMITER_CHARACTER))
                .append(" ")
                .append(text)
                .append(" ")
                .append(repeatString(rightFill, LINE_DELIMITER_CHARACTER))
                .toString();
    }

    /**
     * Returns a line with delimiter characters decided be
     * {@link Formatter#COLUMN_WIDTH} and
     * {@link Formatter#LINE_DELIMITER_CHARACTER}
     *
     * @return
     */
    public static String getDelimiterLine() {
        return repeatString(COLUMN_WIDTH, LINE_DELIMITER_CHARACTER);
    }

    /**
     * Utility method for repeating a string a number of times
     * @param times
     * @param string
     * @return 
     */
    private static String repeatString(int times, String string) {
        return String.join("", Collections.nCopies(times, string));
    }

    /**
     * Takes a string and makes sure that the string has a size specified in the
     * parameter by either trimming the string if it's to long or right padding
     * it if it's to short
     * 
     * @param size
     * @param maybeValue
     * @return 
     */
    public static String fitToSize(int size, Optional<String> maybeValue) {

        if (maybeValue.isPresent()) {
            String value = maybeValue.get();
            return String.format("%-" + size + "s", trimToSize(size, value));
        } else {
            return String.format("%-" + size + "s", "");
        }
    }

}
