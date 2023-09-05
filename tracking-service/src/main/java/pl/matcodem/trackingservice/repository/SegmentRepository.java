package pl.matcodem.trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matcodem.trackingservice.entity.Segment;

import java.util.List;

public interface SegmentRepository extends JpaRepository<Segment, String> {

    List<Segment> findSegmentsByDepartureAndArrivalAirports(String departureIcao, String arrivalIcao);
}
