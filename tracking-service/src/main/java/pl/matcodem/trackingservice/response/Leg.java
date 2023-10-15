package pl.matcodem.trackingservice.response;

import lombok.Builder;
import pl.matcodem.trackingservice.enums.CabinClass;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record Leg(Integer durationMinutes,
                  Integer stopoverDurationMinutes,
                  String departureAirportCode,
                  String arrivalAirportCode,
                  String airlineCode,
                  Set<CabinClass> cabins,
                  String designatorCode,
                  LocalDateTime departureDateTime,
                  LocalDateTime arrivalDateTime) {
}
