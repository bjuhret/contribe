package se.contribe.store.data;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.function.Function;
import se.contribe.store.pojos.BookDetailed;

/**
 * Provides the ability to parse a comma separated string into the object {@link BookDetailed}
 * 
 * @author bjuhr
 */
public class StringToBookParser implements Function<String, BookDetailed> {

    private static final String BOOK_COLUMN_DELIMITER = ";";

    @Override
    public BookDetailed apply(final String line) {
        
        Scanner scanner = new Scanner(line).useDelimiter(BOOK_COLUMN_DELIMITER);
        final String title = scanner.next();
        final String author = scanner.next();
        final BigDecimal price = scanner.nextBigDecimal();
        final int itemsInStock = scanner.nextInt();
        return new BookDetailed(title, author, price, itemsInStock);
    }

}
