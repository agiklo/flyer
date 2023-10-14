package pl.matcodem.reservationservice.infrastructure.flight;

import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;

public interface FlightService {
    FlightResponse getFlightInfo(FlightNumber flightNumber);
    boolean flightExists(FlightNumber flightNumber);
}
