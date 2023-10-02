package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Trip;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> getTripsByDepartureIcaoCode(String code);
    List<Trip> getTripsByArrivalIcaoCode(String code);
    List<Trip> getTripsByIcaoCodesAndDepatureDate(String departureCode, String arrivalCode, LocalDateTime date);
//    List<Trip> findTripsByDepartureDateTime_Date(LocalDate date);
}
