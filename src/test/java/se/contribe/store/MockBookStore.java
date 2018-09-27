package se.contribe.store;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import se.contribe.store.data.MockURLBookListReader;
import se.contribe.store.pojos.Book;
import se.contribe.store.pojos.BookDetailed;

/**
 * Mock version of book store that allows intercepting calls to the parent via
 * consumers
 *
 * @author bjuhr
 */
public class MockBookStore extends BookStore {

    private BiConsumer<Book, Integer> removeConsumer;
    private BiConsumer<Book, Integer> addConsumer;
    private BiConsumer<String, Book[]> searchConsumer;
    private Consumer<Book[]> listConsumer;

    @Override
    public boolean remove(Book book, int quantity) {
        if (removeConsumer != null) {
            removeConsumer.accept(book, quantity);
        }
        return super.remove(book, quantity);
    }

    @Override
    public boolean add(Book book, int quantity) {
        if (addConsumer != null) {
            addConsumer.accept(book, quantity);
        }

        return super.add(book, quantity);
    }

    @Override
    public Book[] list(String searchString) {

        Book[] list = super.list(searchString);
        if (searchConsumer != null) {
            searchConsumer.accept(searchString, list);
        }
        return list;
    }

    @Override
    public Book[] list() {
        Book[] list = super.list();
        if (listConsumer != null) {
            listConsumer.accept(list);
        }
        return list;
    }

    public void setRemoveConsumer(BiConsumer<Book, Integer> removeConsumer) {
        this.removeConsumer = removeConsumer;
    }

    public void setAddConsumer(BiConsumer<Book, Integer> addConsumer) {
        this.addConsumer = addConsumer;
    }

    public void setSearchConsumer(BiConsumer<String, Book[]> searchConsumer) {
        this.searchConsumer = searchConsumer;
    }

    public void setListConsumer(Consumer<Book[]> listConsumer) {
        this.listConsumer = listConsumer;
    }

    @Override
    protected Set<BookDetailed> getAllBooks() {
        return new MockURLBookListReader().getBooks();
    }

}
