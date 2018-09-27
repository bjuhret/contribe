package se.contribe.store.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import se.contribe.store.DataRetreivalException;
import se.contribe.store.Settings;
import se.contribe.store.pojos.BookDetailed;

/**
 * Connects to remote URL to download and parse the book list 
 * 
 * @author bjuhr
 */
public class URLBookListReader {

    private static final Charset CHARACTER_SET = StandardCharsets.UTF_8;

    protected String getConnectionEndPoint() {
        return Settings.getInstance().getBookStoreURL();
    }

    /**
     * Connects to the book list repository and returns a detailed booklist as result. 
     * 
     * @return Set of books with inventory statuses
     */
    public Set<BookDetailed> getBooks() {
        return loadBooks(new StringToBookParser());
    }

    /**
     * Opens the book list stream and uses the converter to parse and return a book list
     * @param converter
     * @return 
     */
    protected Set<BookDetailed> loadBooks(Function<String, BookDetailed> converter) {
        final String url = getConnectionEndPoint();
        try (
                final InputStream inputStream = 
                        new URL(getConnectionEndPoint()).openConnection().getInputStream();
                final InputStreamReader inputStreamReader = 
                        new InputStreamReader(inputStream,CHARACTER_SET);
                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            
            return bufferedReader
                    .lines()
                    .map(line -> converter.apply(line))
                    .collect(Collectors.toSet());
            
        } catch (IOException ex) {
            throw new DataRetreivalException(ex);
        }
    }

}
