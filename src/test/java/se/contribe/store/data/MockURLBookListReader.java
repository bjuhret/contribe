package se.contribe.store.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import se.contribe.store.DataRetreivalException;
import se.contribe.store.Settings;
import se.contribe.store.pojos.BookDetailed;

/**
 *
 * @author bjuhr
 */
public class MockURLBookListReader extends URLBookListReader{

    private final static String BOOKS_DATA_PATH = "META-INF/books.txt";
    private static final Charset CHARACTER_SET = StandardCharsets.UTF_8;
    
    
    @Override
    protected Set<BookDetailed> loadBooks(Function<String, BookDetailed> converter) {
        try (
                final InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream(BOOKS_DATA_PATH);
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
