package pl.matcodem.reservationservice.infrastructure.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(
        String designatorCode,
        String departureAirport,
        String arrivalAirport,
        String airline,
        String aircraft,
        String cabin,
        Integer durationMinutes,
        LocalDateTime departureDateTime,
        LocalDateTime arrivalDateTime,
        BigDecimal ticketPrice,
        BigDecimal extraFees
) {}


