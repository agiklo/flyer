package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Airport;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, String> {

    Optional<Airport> getAirportByIcaoCode(String icaoCode);
}
