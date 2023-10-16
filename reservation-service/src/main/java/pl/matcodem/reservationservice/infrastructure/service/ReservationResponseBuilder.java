package pl.matcodem.reservationservice.infrastructure.service;

import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;

import java.math.BigDecimal;

@Service
public class ReservationResponseBuilder {
    public ReservationResponse build(
            ReservationId reservationId,
            ReservationDate reservationDate,
            Passenger passenger,
            ReservationResponse.FlightInfo flightInfo
    ) {
        return ReservationResponse.builder()
                .reservationId(reservationId)
                .reservationDate(reservationDate)
                .passenger(passenger)
                .flightInfo(flightInfo)
                .price(BigDecimal.ZERO)
                .build();
    }
}
