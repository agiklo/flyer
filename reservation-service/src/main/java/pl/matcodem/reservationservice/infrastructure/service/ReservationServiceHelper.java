package pl.matcodem.reservationservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;
import pl.matcodem.reservationservice.exceptions.SeatNotAvailableException;
import pl.matcodem.reservationservice.infrastructure.flight.FlightResponse;
import pl.matcodem.reservationservice.infrastructure.flight.FlightService;
import pl.matcodem.reservationservice.infrastructure.mapper.FlightResponseMapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            ReservationId reservationId, ReservationDate reservationDate, List<Passenger> passengers, ReservationResponse.FlightInfo flightInfo
    ) {
        return responseBuilder.build(reservationId, reservationDate, passengers, flightInfo);
    }

    public void reserveSeatInClass(FlightNumber flightNumber, String seat) {
        // TODO: Implement
    }

    Set<String> validateChoosenSeats(FlightNumber flightNumber, List<Passenger> passengers) {
        Set<Passenger> passengersWithSeats = passengers.stream()
                .filter(passenger -> passenger.seat() != null)
                .collect(Collectors.toSet());

        if (passengersWithSeats.isEmpty()) {
            return Collections.emptySet();
        }

        passengersWithSeats.forEach(passenger -> {
            boolean isSeatAvailable = isSeatAvailableInClass(flightNumber.number(), passenger.ticketClass(), passenger.seat());
            if (!isSeatAvailable) {
                throw new SeatNotAvailableException("Selected seat is not available");
            }
        });

        return passengersWithSeats.stream()
                .map(Passenger::seat)
                .collect(Collectors.toSet());
    }

    private boolean isSeatAvailableInClass(String flightNumber, String ticketClass, String seat) {
        // TODO: Implement
        return true;
    }
}
