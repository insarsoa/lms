package my.project.lms.exceptionhandler;

import my.project.lms.exceptions.BookNotFoundException;
import my.project.lms.exceptions.InvalidBookException;
import my.project.lms.exceptions.NoAvailableCopiesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException bnf) {
        return new ResponseEntity<>(bnf.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAvailableCopiesException.class)
    public ResponseEntity<String> handleNoAvailableCopiesException(NoAvailableCopiesException nac) {
        return new ResponseEntity<>(nac.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookException.class)
    public ResponseEntity<String> handleInvalidBookException(InvalidBookException ib) {
        return new ResponseEntity<>(ib.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception exception, WebRequest request) {
        return new ResponseEntity<>("Invalid JWT Token: " + exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
