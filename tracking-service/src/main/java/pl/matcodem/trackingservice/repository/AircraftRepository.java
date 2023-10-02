package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {
}
