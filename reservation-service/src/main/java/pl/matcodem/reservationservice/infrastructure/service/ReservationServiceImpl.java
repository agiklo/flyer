package pl.matcodem.reservationservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.matcodem.reservationservice.application.request.ReservationRequest;
import pl.matcodem.reservationservice.application.request.UpdateReservationRequest;
import pl.matcodem.reservationservice.domain.model.Reservation;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;
import pl.matcodem.reservationservice.domain.repository.ReservationRepository;
import pl.matcodem.reservationservice.domain.service.ReservationService;
import pl.matcodem.reservationservice.exceptions.ReservationNotFoundException;
import pl.matcodem.reservationservice.infrastructure.validator.DeletableStatusValidator;
import pl.matcodem.reservationservice.infrastructure.validator.ModificationPeriodValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    public static final String RESERVATION_NOT_FOUND = "Reservation not found";

    private final ReservationRepository reservationRepository;
    private final RestTemplate restTemplate;
    private final DeletableStatusValidator deletableStatusValidator;
    private final ModificationPeriodValidator modificationPeriodValidator;

    @Override
    public Reservation createReservation(ReservationRequest request) {
        String flightNumber = request.getFlightNumber().number();

        if (!flightExists(flightNumber)) {
            throw new NullPointerException("Flight with number " + flightNumber + " not found");
        }

        Reservation reservation = new Reservation(
                ReservationId.generate(),
                ReservationCode.of(request.getReservationCode().code()),
                request.getReservationDate(),
                request.getPassenger(),
                new FlightNumber(flightNumber),
                FlightReservationStatus.PENDING
        );

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(UpdateReservationRequest request) {
        ReservationId reservationId = request.reservationId();
        Passenger newPassenger = request.newPassenger();

        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));

        modificationPeriodValidator.isReservationWithinAllowedModificationPeriod(existingReservation);

        existingReservation.setPassenger(newPassenger);

        return reservationRepository.save(existingReservation);
    }

    private boolean flightExists(String flightNumber) {
        ResponseEntity<Void> response = restTemplate.getForEntity(
                "http://tracking-service/api/flights/{flightNumber}",
                Void.class,
                flightNumber
        );

        return response.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public Optional<Reservation> getReservationById(ReservationId reservationId) {
        return reservationRepository.findById(reservationId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void cancelReservation(ReservationId reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(FlightReservationStatus.CANCELED);
            reservationRepository.save(reservation);
        } else {
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public void deleteReservation(ReservationId reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));

        deletableStatusValidator.validateDeletableStatus(reservation.getStatus());

        reservationRepository.delete(reservation);
    }
}

