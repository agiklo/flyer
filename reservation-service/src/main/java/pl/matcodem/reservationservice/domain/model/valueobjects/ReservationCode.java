package pl.matcodem.reservationservice.domain.model.valueobjects;

public record ReservationCode(String code) {
    public static ReservationCode of(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation code must not be empty");
        }
        return new ReservationCode(code);
    }
}

