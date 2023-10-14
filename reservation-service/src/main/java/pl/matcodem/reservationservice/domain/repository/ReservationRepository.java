package pl.matcodem.reservationservice.domain.repository;


import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findById(ReservationId reservationId);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

    List<Reservation> getReservationsByStatusAndDate(FlightReservationStatus status, ReservationDate date);
}

