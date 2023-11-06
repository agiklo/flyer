package pl.matcodem.reservationservice.application.events;

import java.util.List;

public record ReservationCreatedEvent(String reservationId, List<String> passengerPesels,
                                      String flightDetails) implements Event {
}

