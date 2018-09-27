
package se.contribe.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides an interface for settings used in the book shop
 * @author bjuhr
 */
public class Settings {

    private final static String PROPERTIES_PATH = "META-INF/config.properties";
    private final static String BOOK_LIST_URL_PROP_KEY = "book.list.endpoint";
    private static final Settings INSTANCE;
    private static Properties properties;
    private static final String CANNOT_FIND_FILE = "Cannot find properties file using path ";
    
    private Settings(){
    }
    
    public static Settings getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new Settings();
        Settings.loadProperties();
    }

    public String getBookStoreURL() {
        return Settings.properties.getProperty(BOOK_LIST_URL_PROP_KEY);
    }
    
    public static void main(String[] args) {
        Settings instance = Settings.getInstance();
        System.out.println(instance.getBookStoreURL());
    }

    private static void loadProperties() {

        InputStream input = null;
        try {
            input = Settings.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
            if (input == null) {
                throw new IllegalArgumentException(CANNOT_FIND_FILE + PROPERTIES_PATH);
            }
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
