package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Airline;

public interface AirLineRepository extends JpaRepository<Airline, String> {
}
