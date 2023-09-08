package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {

    List<Flight> findFlightsByDepartureAndArrivalAirports(String departureIcao, String arrivalIcao);
    List<Flight> findFlightsByDepartureAirportAndDepartureDateTimeAfter(String departureIcao, LocalDateTime date);
    List<Flight> findFlightsByArrivalAirport(String arrivalIcao);
}
