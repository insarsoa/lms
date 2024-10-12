package my.project.lms.service;

import my.project.lms.model.Book;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class Library {
    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();
    private final Cache<String, Book> bookCache;
    private final CacheManager cacheManager;

    // Constructor with cache setup
    public Library() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("bookCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, Book.class, ResourcePoolsBuilder.heap(100)))
                .build(true);
        bookCache = cacheManager.getCache("bookCache", String.class, Book.class);
    }

    public void addBook(Book book) {
        books.put(book.isbn, book);
    }

    public void removeBook(String isbn) {
        books.remove(isbn);
        bookCache.remove(isbn);
    }

    public Book findBookByISBN(String isbn) {
        Book book = bookCache.get(isbn);
        if (book == null) {
            book = books.get(isbn);
            if (book != null) {
                bookCache.put(isbn, book);
            }
        }
        return book;
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.author.equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findAllBooks() {
        return books.values().stream().sorted()
                .collect(Collectors.toList());
    }

    public synchronized boolean borrowBook(String isbn) {
        Book book = findBookByISBN(isbn);
        if (book != null && book.availableCopies > 0) {
            book.borrowBook();
            return true;
        }
        return false;
    }

    public synchronized void returnBook(String isbn) {
        Book book = findBookByISBN(isbn);
        if (book != null) {
            book.returnBook();
        }
    }

    public void closeCache() {
        cacheManager.close();
    }
}
