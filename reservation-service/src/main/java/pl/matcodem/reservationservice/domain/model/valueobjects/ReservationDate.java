package pl.matcodem.reservationservice.domain.model.valueobjects;

import java.time.LocalDate;

public record ReservationDate(LocalDate date) {
    public ReservationDate {
        if (date == null) {
            throw new IllegalArgumentException("Reservation date must not be null");
        }
    }
}
