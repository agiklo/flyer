package pl.matcodem.reservationservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.infrastructure.flight.FlightResponse;
import pl.matcodem.reservationservice.infrastructure.flight.FlightService;
import pl.matcodem.reservationservice.infrastructure.mapper.FlightResponseMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceHelper {

    private final FlightService flightService;
    private final FlightResponseMapper responseMapper;
    private final ReservationResponseBuilder responseBuilder;

    void ensureFlightExists(FlightNumber flightNumber) {
        if (!flightService.flightExists(flightNumber)) {
            throw new NullPointerException("Flight with number " + flightNumber.number() + " not found");
        }
    }

    ReservationResponse.FlightInfo getFlightInfo(FlightNumber flightNumber) {
        FlightResponse flightResponse = flightService.getFlightInfo(flightNumber);
        return responseMapper.flightResponseToFlightInfo(flightResponse);
    }

    ReservationResponse buildReservationResponse(
            ReservationDate reservationDate, Passenger passenger, ReservationResponse.FlightInfo flightInfo
    ) {
        return responseBuilder.build(reservationDate, passenger, flightInfo);
    }
}
