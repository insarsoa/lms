package my.project.lms.exceptions;

public class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(String isbn) {
        super("No available copies for book with ISBN " + isbn + ".");
    }
}

