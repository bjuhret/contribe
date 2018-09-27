package se.contribe.store;

/**
 * Provides inventory statuses for books
 * 
 * @author bjuhr
 */
public enum BookStockStatus {
    /**
     * The book exists in stock, number code = 0
     */
    OK(0), 
    /**
     * The book exists as an inventory item but is currently not in stock, number code = 1
     */
    NOT_IN_STOCK(1), 
    /**
     * The book does not exists as an inventory item, number code = 2
     */
    DOES_NOT_EXIST(2);
    private final int numberCode;

    private BookStockStatus(int numberCode) {
        this.numberCode = numberCode;
    }

    /**
     * Returns the number code for the status
     * @return 
     */
    public int asNumberCode() {
        return numberCode;
    }

}
