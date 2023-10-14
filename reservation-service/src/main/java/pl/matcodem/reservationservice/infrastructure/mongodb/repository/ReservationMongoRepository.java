package pl.matcodem.reservationservice.infrastructure.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.infrastructure.mongodb.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReservationMongoRepository extends MongoRepository<ReservationEntity, String> {

    List<ReservationEntity> getReservationEntitiesByStatusAndReservationDate(FlightReservationStatus status, LocalDate reservationDate);

}

