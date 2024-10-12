package my.project.lms.exceptions;

public class InvalidBookException extends RuntimeException {
    public InvalidBookException(String message) {
        super(message);
    }
}
