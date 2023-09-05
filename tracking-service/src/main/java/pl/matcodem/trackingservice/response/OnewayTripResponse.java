package pl.matcodem.trackingservice.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class OnewayTripResponse {
    private Long flightId;
    private LocalTime departureTime;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private Set<String> airlineCodes;
    private Set<String> stopoverAirportCodes;
    private Set<String> allianceCodes;
    private Integer stopoversCount;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Integer stopoverDurationMinutes;
    private Integer durationMinutes;
    private Boolean overnight;
    private LocalTime stopoverDuration;
    private Integer durationDays;
    private Boolean longStopover;
    private Set<SegmentResponse> segments;
    private Boolean shortStopover;
    private Boolean earlyDeparture;
    private Boolean lateArrival;
    private Boolean newAircraft;
    private Boolean oldAircraft;
    private Boolean highlyRatedCarrier;
    private Double score;
}
