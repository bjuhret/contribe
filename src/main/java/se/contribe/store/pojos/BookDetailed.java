package se.contribe.store.pojos;

import java.math.BigDecimal;

/**
 * Extends book to also take warehouse details into consideration (items in stock)
 * @author bjuhr
 */
public class BookDetailed extends Book {

    private final int itemsInStock;

    public BookDetailed(String title, String author, BigDecimal price, int itemsInStock) {
        super(title, author, price);
        this.itemsInStock = itemsInStock;
    }

    public int getItemsInStock() {
        return itemsInStock;
    }


}
