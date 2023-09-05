package pl.matcodem.trackingservice.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Leg(Integer durationMinutes,
                  Integer stopoverDurationMinutes,
                  String departureAirportCode,
                  String arrivalAirportCode,
                  String airlineCode,
                  String cabin,
                  String designatorCode,
                  LocalDateTime departureDateTime,
                  LocalDateTime arrivalDateTime) {
}
