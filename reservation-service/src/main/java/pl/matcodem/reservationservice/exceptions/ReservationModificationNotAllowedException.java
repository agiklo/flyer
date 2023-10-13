package pl.matcodem.reservationservice.exceptions;

public class ReservationModificationNotAllowedException extends RuntimeException {
    public ReservationModificationNotAllowedException(String message) {
        super(message);
    }

    public ReservationModificationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}

