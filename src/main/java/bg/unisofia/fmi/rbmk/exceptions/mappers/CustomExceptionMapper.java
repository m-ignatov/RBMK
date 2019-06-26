package bg.unisofia.fmi.rbmk.exceptions.mappers;

import bg.unisofia.fmi.rbmk.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A simple exception mapper that provides error handling functionality across all controllers
 */
@RestControllerAdvice
public class CustomExceptionMapper extends ResponseEntityExceptionHandler {

    /**
     * Handles bad request exceptions
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request) {
        return toResponseEntity(exception, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles forbidden exceptions
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException exception, WebRequest request) {
        return toResponseEntity(exception, HttpStatus.FORBIDDEN, request);
    }

    /**
     * Handles not found exceptions
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        return toResponseEntity(exception, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles unauthorized exceptions
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleNotAuthorizedException(NotAuthorizedException exception, WebRequest request) {
        return toResponseEntity(exception, HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Handles all other exceptions
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleAll(Exception exception, WebRequest request) {
        return toResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> toResponseEntity(Exception exception, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(status, exception.getLocalizedMessage(), request);
        return new ResponseEntity<>(apiError, new HttpHeaders(), status);
    }
}
