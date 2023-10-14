package pl.matcodem.reservationservice.infrastructure.mongodb.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;
import pl.matcodem.reservationservice.domain.repository.ReservationRepository;
import pl.matcodem.reservationservice.infrastructure.mongodb.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationMongoRepository repository;

    @Override
    public Optional<Reservation> findById(ReservationId id) {
        Optional<ReservationEntity> reservationEntity = repository.findById(id.value());
        if (reservationEntity.isPresent()) {
            Reservation reservation = reservationEntity.get().toReservation();
            return Optional.ofNullable(reservation);
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        List<ReservationEntity> reservationEntities = repository.findAll();
        return reservationEntities.stream()
                .map(ReservationEntity::toReservation)
                .toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = ReservationEntity.fromReservation(reservation);
        ReservationEntity savedEntity = repository.save(reservationEntity);
        return savedEntity.toReservation();
    }

    @Override
    public void delete(Reservation reservation) {
        ReservationEntity reservationEntity = ReservationEntity.fromReservation(reservation);
        repository.delete(reservationEntity);
    }

    @Override
    public List<Reservation> getReservationsByStatusAndDate(FlightReservationStatus status, ReservationDate date) {
        List<ReservationEntity> reservationEntities = repository.getReservationEntitiesByStatusAndReservationDate(status, date.date());
        return reservationEntities.stream()
                .map(ReservationEntity::toReservation)
                .toList();
    }
}
