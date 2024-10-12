package my.project.lms;

import my.project.lms.model.Book;
import my.project.lms.service.Library;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceUnitTest {

    private Library library;
    @InjectMocks
    private static Book book;
    private static Book book1;
    private static Book book2;

    @BeforeAll
    static void setup() {
        addSampleBooks();
    }

    @BeforeEach
    void init() {
        library = new Library();
    }

    @Test
    void testAddBook() {
        library.addBook(book);
        assertEquals(book, library.findBookByISBN("12345"));
    }

    @Test
    void testRemoveBook() {
        library.addBook(book);
        library.removeBook("12345");
        assertNull(library.findBookByISBN("12345"));
    }

    @Test
    void testFindBookByISBN() {

        library.addBook(book);
        Book foundBook = library.findBookByISBN("12345");
        assertNotNull(foundBook);
        assertEquals("12345", foundBook.isbn);
    }

    @Test
    void testFindBooksByAuthor() {
        library.addBook(book);
        library.addBook(book1);
        library.addBook(book2);

        List<Book> booksByAuthor = library.findBooksByAuthor("Arul");
        assertEquals(2, booksByAuthor.size());
    }

    @Test
    void testBorrowBookSuccess() {

        library.addBook(book);

        boolean success = library.borrowBook("12345");
        assertTrue(success);
        assertEquals(4, book.availableCopies);
    }

    @Test
    void testBorrowBookFailure() {
        book.setAvailableCopies(0);
        library.addBook(book);

        boolean success = library.borrowBook("12345");
        assertFalse(success);
        assertEquals(0, book.availableCopies);
    }

    @Test
    void testReturnBook() {
        book.setAvailableCopies(10);
        library.addBook(book);
        library.borrowBook("12345");

        library.returnBook("12345");
        assertEquals(10, book.availableCopies);
    }

    public static void addSampleBooks() {

        book = Book.builder()
                .isbn("12345")
                .title("Python")
                .author("Arul")
                .publicationYear(2024)
                .availableCopies(5)
                .build();

        book1 = Book.builder()
                .isbn("11111")
                .title("Java")
                .author("Arul")
                .publicationYear(2010)
                .availableCopies(8)
                .build();

        book2 = Book.builder()
                .isbn("22222")
                .title("")
                .author("Anthuvan")
                .publicationYear(2021)
                .availableCopies(11)
                .build();
    }
}
