package pl.matcodem.trackingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException() {
        super();
    }

    public AirportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirportNotFoundException(String icaoCode) {
        super("Airport with ICAO code %s not found.".formatted(icaoCode));
    }
}
