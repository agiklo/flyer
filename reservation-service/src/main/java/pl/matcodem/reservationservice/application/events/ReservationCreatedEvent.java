package pl.matcodem.reservationservice.application.events;

public record ReservationCreatedEvent(String reservationId, String passengerName,
                                      String flightDetails) implements Event {
}

