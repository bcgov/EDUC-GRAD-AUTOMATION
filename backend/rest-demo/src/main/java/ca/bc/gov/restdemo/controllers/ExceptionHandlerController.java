package ca.bc.gov.restdemo.controllers;


import ca.bc.gov.restdemo.exceptions.ConflictException;
import ca.bc.gov.restdemo.exceptions.NotFoundException;
import ca.bc.gov.restdemo.exceptions.ServiceUnavailableException;
import ca.bc.gov.restdemo.exceptions.UnrecoverableException;
import ca.bc.gov.restdemo.model.EventDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Chris.Ditcher
 * This REST controller implements methods for handling errors encountered by
 * other controllers.
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    /**
     * Constructor injection
     * @param messageSource
     */
    @Autowired
    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handler for NotFoundException
     * @param ex
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<EventDetails> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                createEventDetails(ex.getMessage(), ""),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handles a ServiceUnavailableException
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public final ResponseEntity<EventDetails> handleServiceUnavailableException(ServiceUnavailableException ex) {
        return new ResponseEntity<>(
                createEventDetails(ex.getMessage(), ""),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(UnrecoverableException.class)
    public final ResponseEntity<EventDetails> handleUnrecoverableException(UnrecoverableException ex) {
        return new ResponseEntity<>(
                createEventDetails(ex.getMessage(), "Something has gone horribly wrong =("),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public final ResponseEntity<EventDetails> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                createEventDetails(ex.getMessage(), ""),
                HttpStatus.BAD_REQUEST
        );
    }

    // TODO: override methods for finer grained error handling as per below example

    /**
     * Example for handling some common errors like MessageNotReadableExceptions etc.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        EventDetails details = createEventDetails(
                "There was an error parsing the JSON message body.",
                "Please ensure all fields are valid and dates are formatted as: pattern=\"yyyy-MM-dd HH:mm:ss\""
        );
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for IllegalArgumentException. Handy for catching nested
     * validation errors.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<EventDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        EventDetails details = createEventDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    /**
     * Convenience method for creating EventDetails
     * @param message
     * @param details
     * @return
     */
    private static EventDetails createEventDetails(String message, String details){
        return new EventDetails(
                LocalDateTime.now(),
                message,
                details
        );
    }

}
