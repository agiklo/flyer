package pl.matcodem.reservationservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.events.ReservationCreatedEvent;
import pl.matcodem.reservationservice.application.request.ReservationRequest;
import pl.matcodem.reservationservice.application.request.UpdateReservationRequest;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;
import pl.matcodem.reservationservice.domain.repository.ReservationRepository;
import pl.matcodem.reservationservice.domain.service.ReservationService;
import pl.matcodem.reservationservice.exceptions.ReservationNotFoundException;
import pl.matcodem.reservationservice.infrastructure.kafka.KafkaEventProducer;
import pl.matcodem.reservationservice.infrastructure.kafka.KafkaTopics;
import pl.matcodem.reservationservice.infrastructure.validator.DeletableStatusValidator;
import pl.matcodem.reservationservice.infrastructure.validator.ModificationPeriodValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    public static final String RESERVATION_NOT_FOUND = "Reservation not found";

    private final ReservationRepository repository;
    private final ReservationServiceHelper helper;
    private final DeletableStatusValidator deletableStatusValidator;
    private final ModificationPeriodValidator modificationPeriodValidator;
    private final KafkaEventProducer kafkaEventProducer;

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        FlightNumber flightNumber = request.getFlightNumber();
        helper.ensureFlightExists(flightNumber);

        ReservationCode reservationCode = ReservationCode.of(UUID.randomUUID().toString());
        ReservationDate reservationDate = request.getReservationDate();
        var passengers = request.getPassengers();

        Reservation reservation = new Reservation(
                ReservationId.generate(),
                reservationCode,
                reservationDate,
                passengers,
                flightNumber,
                FlightReservationStatus.PENDING
        );
        Reservation savedReservation = repository.save(reservation);

        ReservationCreatedEvent event = new ReservationCreatedEvent(
                savedReservation.getId().value(),
                passengers.stream().map(Passenger::pesel).toList(),
                "Flight details: " + helper.getFlightInfo(flightNumber).toString()
        );

        kafkaEventProducer.sendEvent(KafkaTopics.RESERVATION_CREATED.getTopicName(), event);

        ReservationResponse.FlightInfo flightInfo = helper.getFlightInfo(flightNumber);
        return helper.buildReservationResponse(savedReservation.getId(), reservationDate, passengers, flightInfo);
    }

    @Override
    public ReservationResponse updateReservation(UpdateReservationRequest request) {
        ReservationId reservationId = request.reservationId();
        var newPassengers = request.newPassengers();

        Reservation existingReservation = repository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));

        modificationPeriodValidator.isReservationWithinAllowedModificationPeriod(existingReservation);

        existingReservation.setPassengers(newPassengers);

        Reservation updatedReservation = repository.save(existingReservation);

        FlightNumber flightNumber = updatedReservation.getFlightNumber();
        ReservationResponse.FlightInfo flightInfo = helper.getFlightInfo(flightNumber);

        ReservationDate reservationDate = updatedReservation.getReservationDate();
        return helper.buildReservationResponse(updatedReservation.getId(), reservationDate, newPassengers, flightInfo);
    }


    @Override
    public Optional<ReservationResponse> getReservationResponseById(ReservationId reservationId) {
        return repository.findById(reservationId)
                .map(reservation -> helper.buildReservationResponse(
                        reservationId,
                        reservation.getReservationDate(),
                        reservation.getPassengers(),
                        helper.getFlightInfo(reservation.getFlightNumber())
                ));
    }

    @Override
    public Optional<Reservation> getReservationById(ReservationId reservationId) {
        return repository.findById(reservationId);
    }

    @Override
    public Page<ReservationResponse> getAllReservations(int page, int size) {
        List<Reservation> allReservations = repository.findAll();

        List<ReservationResponse> reservationResponses = allReservations.stream()
                .map(reservation -> helper.buildReservationResponse(
                        reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getPassengers(),
                        helper.getFlightInfo(reservation.getFlightNumber())
                ))
                .toList();

        return new PageImpl<>(reservationResponses, PageRequest.of(page, size), allReservations.size());
    }

    @Override
    public void cancelReservation(ReservationId reservationId) {
        Optional<Reservation> reservationOptional = repository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(FlightReservationStatus.CANCELED);
            repository.save(reservation);
        } else {
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public List<ReservationResponse> getReservationsByStatusAndDate(FlightReservationStatus status, ReservationDate date) {
        List<Reservation> reservations = repository.getReservationsByStatusAndDate(status, date);
        return reservations.stream()
                .map(reservation -> helper.buildReservationResponse(
                        reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getPassengers(),
                        helper.getFlightInfo(reservation.getFlightNumber())
                ))
                .toList();
    }

    @Override
    public void deleteReservation(ReservationId reservationId) {
        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));

        deletableStatusValidator.validateDeletableStatus(reservation.getStatus());

        repository.delete(reservation);
    }
}

