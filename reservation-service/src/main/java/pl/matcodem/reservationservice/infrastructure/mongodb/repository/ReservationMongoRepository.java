package pl.matcodem.reservationservice.infrastructure.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.matcodem.reservationservice.infrastructure.mongodb.entity.ReservationEntity;

public interface ReservationMongoRepository extends MongoRepository<ReservationEntity, String> {
}

