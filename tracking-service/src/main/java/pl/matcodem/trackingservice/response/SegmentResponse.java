package pl.matcodem.trackingservice.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SegmentResponse {
    private Integer durationMinutes;
    private Integer stopoverDurationMinutes;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private String airlineCode;
    private String operatingAirlineCode;
    private String cabin;
    private String designatorCode;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
}
