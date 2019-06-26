package bg.unisofia.fmi.rbmk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

/**
 * Common structure of an error response
 */
public class ApiError {

    /**
     * HTTP status code
     */
    private HttpStatus status;

    /**
     * Message describing the error
     */
    private String message;

    /**
     * Endpoint of origin request
     */
    private String target;

    public ApiError(HttpStatus status, String message, WebRequest request) {
        this.status = status;
        this.target = request.getDescription(false);
        this.message = (Objects.nonNull(message) ? message : status.getReasonPhrase());
    }

    public int getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public String getTarget() {
        return target;
    }
}