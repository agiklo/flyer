package pl.matcodem.reservationservice.domain.model.valueobjects;

public enum FlightReservationStatus {
    PENDING("Pending", "Reservation is pending and has not been confirmed yet"),
    CONFIRMED("Confirmed", "Reservation has been confirmed and is active"),
    CANCELED("Canceled", "Reservation has been canceled by the passenger or the airline"),
    COMPLETED("Completed", "Flight has been completed, and the reservation is closed"),
    NO_SHOW("No Show", "Passenger did not show up for the flight");

    private final String name;
    private final String description;

    FlightReservationStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
