package se.contribe.store;

/**
 * Thrown when there is some problem trying to access the books data store
 * @author bjuhr
 */
public class DataRetreivalException extends RuntimeException{

    public DataRetreivalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataRetreivalException(Throwable cause) {
        super(cause);
    }
    
    
    
}
