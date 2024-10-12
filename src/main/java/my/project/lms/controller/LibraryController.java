package my.project.lms.controller;

import my.project.lms.exceptions.BookNotFoundException;
import my.project.lms.exceptions.InvalidBookException;
import my.project.lms.exceptions.NoAvailableCopiesException;
import my.project.lms.model.Book;
import my.project.lms.service.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private Library library;

    // Add a new book
    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        if (book.isbn == null || book.isbn.isEmpty() || book.title == null || book.author == null) {
            throw new InvalidBookException("Book must have a valid ISBN, title, and author.");
        }
        library.addBook(book);
        return ResponseEntity.ok("Book added successfully.");
    }

    // Remove a book by ISBN
    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<String> removeBook(@PathVariable String isbn) {
        Book book = library.findBookByISBN(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        library.removeBook(isbn);
        return ResponseEntity.ok("Book removed successfully.");
    }

    // Find a book by ISBN
    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> findBookByISBN(@PathVariable String isbn) {
        Book book = library.findBookByISBN(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        return ResponseEntity.ok(book);
    }

    // Find books by author
    @GetMapping("/books")
    public ResponseEntity<List<Book>> findBooksByAuthor(@RequestParam String author) {
        if (author == null || author.isEmpty()) {
            throw new InvalidBookException("Author cannot be empty.");
        }
        List<Book> books = library.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    // Borrow a book by ISBN
    @PostMapping("/books/borrow/{isbn}")
    public ResponseEntity<String> borrowBook(@PathVariable String isbn) {
        Book book = library.findBookByISBN(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        boolean success = library.borrowBook(isbn);
        if (!success) {
            throw new NoAvailableCopiesException(isbn);
        }

        return ResponseEntity.ok("Book borrowed successfully.");
    }

    // Return a book by ISBN
    @PostMapping("/books/return/{isbn}")
    public ResponseEntity<String> returnBook(@PathVariable String isbn) {
        Book book = library.findBookByISBN(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        library.returnBook(isbn);
        return ResponseEntity.ok("Book returned successfully.");
    }
}
