package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> getFlightsByDepartureIcaoCode(String code);
    List<Flight> getFlightsByArrivalIcaoCode(String code);
    List<Flight> findFlightsByDepartureDateTime_Date(LocalDate date);
}
