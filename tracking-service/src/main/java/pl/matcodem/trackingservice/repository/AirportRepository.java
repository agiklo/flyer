package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, String> {

    Airport getAirportByIcaoCode(String icaoCode);
}
