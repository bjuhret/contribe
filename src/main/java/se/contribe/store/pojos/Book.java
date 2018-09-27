package se.contribe.store.pojos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import se.contribe.store.console.util.Formatter;

/**
 * Book object representing a book
 * 
 * @author bjuhr
 */
public class Book implements Comparable<Book> {

    private final String title;
    private final String author;
    private final BigDecimal price;

    public Book(String title, String author, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.price = price.setScale(4, RoundingMode.HALF_UP);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.title);
        hash = 23 * hash + Objects.hashCode(this.author);
        hash = 23 * hash + Objects.hashCode(this.price);
        return hash;
    }

    /**
     * A book equals another book if it has the same title, author, and price
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "title=" + title + " author=" + author + " price=" + price.setScale(2).toPlainString();
    }

    /**
     * Comparison is done according to the following precedence:
     * <ol>
     * <li>title</li>
     * <li>author</li>
     * <li>price</li>
     * </ol>
     * 
     * The items above will be compared using natural order
     * 
     * @param that
     * @return 
     */
    @Override
    public int compareTo(Book that) {

        if (this == that) {
            return 0;
        }

        final int titleCompare = this.getTitle().compareTo(that.getTitle());
        if (titleCompare != 0) {
            return titleCompare;
        };

        final int authorCompare = this.getAuthor().compareTo(that.getAuthor());
        if (authorCompare != 0) {
            return authorCompare;
        };

        return this.getPrice().compareTo(that.getPrice());
    }

    /**
     * For console printing
     * @return 
     */
    public StringBuilder formatted() {
        return new StringBuilder()
            .append(Formatter.fitToSize(20, Optional.ofNullable(this.getTitle())))
            .append(Formatter.fitToSize(20, Optional.ofNullable(this.getAuthor())))
            .append(Formatter.fitToSize(15, Optional.ofNullable(this.getPrice().setScale(2).toPlainString())));

    }

}
