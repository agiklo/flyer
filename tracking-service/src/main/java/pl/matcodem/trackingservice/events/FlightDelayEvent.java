package pl.matcodem.trackingservice.events;

import java.time.LocalDateTime;

public record FlightDelayEvent(String designatorCode, LocalDateTime newDepartureDateTime, Integer delayTimeMinutes) {
}
