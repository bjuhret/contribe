package se.contribe.store.data;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.contribe.store.pojos.BookDetailed;

/**
 *
 * @author bjuhr
 */
public class StringToBookParserTest {

    /**
     * Test of apply method, of class StringToBookParser.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        String line = "Generic Title;Second Author;1,748.00;3";
        StringToBookParser parser = new StringToBookParser();
        BookDetailed parsedBook = parser.apply(line);
        BookDetailed instantiatedBook = new BookDetailed("Generic Title", "Second Author", new BigDecimal(1748.00), 3);
        BookDetailed result = parser.apply(line);
        assertEquals(parsedBook,instantiatedBook);
    }
    
}
