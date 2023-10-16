package pl.matcodem.reservationservice.domain.service;

import org.springframework.data.domain.Page;
import pl.matcodem.reservationservice.application.request.ReservationRequest;
import pl.matcodem.reservationservice.application.request.UpdateReservationRequest;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<ReservationResponse> getReservationResponseById(ReservationId reservationId);
    Optional<Reservation> getReservationById(ReservationId reservationId);

    Page<ReservationResponse> getAllReservations(int page, int size);

    ReservationResponse createReservation(ReservationRequest request);

    ReservationResponse updateReservation(UpdateReservationRequest request);

    void deleteReservation(ReservationId reservationId);

    void cancelReservation(ReservationId reservationId);

    List<ReservationResponse> getReservationsByStatusAndDate(FlightReservationStatus status, ReservationDate date);
}


