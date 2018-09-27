package se.contribe.store;

import static org.junit.Assert.*;


/**
 *
 * @author bjuhr
 */
public class SettingsTest {

    /**
     * Test of getInstance method, of class Settings.
     */
    @org.junit.Test
    public void testGetInstance() {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();
        assertNotNull(settings1);
        assertNotNull(settings2);
        assertSame("The singleton returns different instances", settings1, settings2);
    }

    /**
     * Test of getBookStoreURL method, of class Settings.
     */
    @org.junit.Test
    public void testGetBookStoreURL() {
        Settings instance = Settings.getInstance();
        String expResult = "https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt";
        String result = instance.getBookStoreURL();
        assertEquals(expResult, result);
    }
    
}
