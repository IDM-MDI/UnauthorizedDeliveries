package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * This class handles exceptions thrown by the application controllers and provides a unified error response.
 * @author Dayanch
 */
@RestControllerAdvice
public class ExceptionController {
    /**
     * Handles ServiceException and returns a service exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the custom exception response
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }


    /**
     * Handles MethodArgumentNotValidException and HttpMessageNotReadableException and returns a validation exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the validation exception response
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage()
                );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }


    /**
     * Handles HttpRequestMethodNotSupportedException and returns a custom exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the custom exception response
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupportException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }


    /**
     * Handles NoSuchMethodException and returns a custom exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the custom exception response
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchMethodException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }
    /**
     * Handles ConstraintViolationException and returns a custom exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the custom exception response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }
    /**
     * Handles SQLException and DataAccessException and returns a custom exception response with the corresponding HTTP status code.
     * @param exception the exception to be handled
     * @return the custom exception response
     */

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<String> handleSQLException(SQLException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }
}
