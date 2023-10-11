package pl.matcodem.trackingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to represent a flight not found error.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlightNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of FlightNotFoundException with a custom error message.
     *
     * @param message The custom error message.
     */
    public FlightNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of FlightNotFoundException with a custom error message and a cause.
     *
     * @param message The custom error message.
     * @param cause   The cause of the exception.
     */
    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of FlightNotFoundException with a cause.
     *
     * @param cause The cause of the exception.
     */
    public FlightNotFoundException(Throwable cause) {
        super(cause);
    }
}
