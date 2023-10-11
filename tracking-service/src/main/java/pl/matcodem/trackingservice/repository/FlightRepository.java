package pl.matcodem.trackingservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {

    Page<Flight> findFlightsByDepartureAndArrivalAirports(String departureIcao, String arrivalIcao, Pageable pageable);
    List<Flight> findFlightsByDepartureIcaoCodeAndDateTimeAfter(String departureIcao, LocalDateTime date);
    Page<Flight> findFlightsByDepartureIcaoCodeAndDateTimeAfter(String departureIcao, LocalDateTime date, Pageable pageable);
    Page<Flight> findFlightsByArrivalAirport(String arrivalIcao, Pageable pageable);
    Page<Flight> findFlightsByDepartureAndArrivalAirportsAndDate(String departureIcao, String arrivalIcao, LocalDateTime date, Pageable pageable);
}
