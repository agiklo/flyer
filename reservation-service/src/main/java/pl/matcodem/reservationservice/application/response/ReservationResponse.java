package pl.matcodem.reservationservice.application.response;

import lombok.Builder;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReservationResponse(
        FlightInfo flightInfo,
        BigDecimal price,
        Passenger passenger,
        LocalDate reservationDate
) {
    @Builder
    public record FlightInfo(
            String designatorCode,
            String departureAirport,
            String arrivalAirport,
            String airline,
            String aircraft,
            String cabin,
            Integer durationMinutes,
            LocalDateTime departureDateTime,
            LocalDateTime arrivalDateTime
    ) {}
}
