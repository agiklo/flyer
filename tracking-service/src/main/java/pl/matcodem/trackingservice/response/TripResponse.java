package pl.matcodem.trackingservice.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Builder
public record TripResponse(Long tripId,
                           LocalTime departureTime,
                           String departureAirportCode,
                           String arrivalAirportCode,
                           Set<String> airlineCodes,
                           Set<String> stopoverAirportCodes,
                           Set<String> allianceCodes,
                           Integer stopoversCount,
                           LocalDateTime departureDateTime,
                           LocalDateTime arrivalDateTime,
                           Integer stopoverDurationMinutes,
                           Integer durationMinutes,
                           Boolean overnight,
                           LocalTime stopoverDuration,
                           Integer durationDays,
                           Boolean longStopover,
                           Set<Leg> legs,
                           Boolean shortStopover,
                           Boolean earlyDeparture,
                           Boolean lateArrival,
                           Boolean newAircraft,
                           Boolean oldAircraft,
                           Boolean highlyRatedCarrier,
                           Double score) {
}
