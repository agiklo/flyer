package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Aircraft;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
    Optional<Aircraft> findAircraftByCallSign(String callsign);
}
