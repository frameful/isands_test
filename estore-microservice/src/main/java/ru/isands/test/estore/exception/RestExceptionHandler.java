package ru.isands.test.estore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("Error 406 entity not acceptable: " + ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIncorrectFormatException(IncorrectFormatException ex) {
        return new ResponseEntity<>(new ErrorResponse("Error 400 bad request: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse("Error 409 conflict: " + ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleOutOfStockException(OutOfStockException ex) {
        return new ResponseEntity<>(new ErrorResponse("Error 400 entity not valid: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
