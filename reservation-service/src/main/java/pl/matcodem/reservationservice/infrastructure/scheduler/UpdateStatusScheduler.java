package pl.matcodem.reservationservice.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;
import pl.matcodem.reservationservice.domain.repository.ReservationRepository;
import pl.matcodem.reservationservice.domain.service.ReservationService;
import pl.matcodem.reservationservice.infrastructure.flight.FlightResponse;
import pl.matcodem.reservationservice.infrastructure.flight.FlightService;
import pl.matcodem.reservationservice.util.DateCalculator;

import java.util.List;
import java.util.Optional;

/**
 * A scheduler that periodically updates reservation statuses.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateStatusScheduler {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final FlightService flightService;
    private final DateCalculator dateCalculator;

    @Value("${scheduler.fixedRate}")
    private long schedulerFixedRate;

    /**
     * Periodically updates the status of reservations that have taken place.
     */
    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void updateReservationStatus() {
        log.info("Updating reservation statuses...");
        List<ReservationResponse> confirmedReservations = getConfirmedReservationsForToday();
        confirmedReservations.stream()
                .map(this::getReservationById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(this::flightHasTakenPlace)
                .forEach(this::updateReservationStatusToCompleted);
        log.info("Reservation status update completed.");
    }

    private List<ReservationResponse> getConfirmedReservationsForToday() {
        return reservationService.getReservationsByStatusAndDate(FlightReservationStatus.CONFIRMED, new ReservationDate(dateCalculator.getCurrentDate()));
    }

    private Optional<Reservation> getReservationById(ReservationResponse reservationResponse) {
        return reservationService.getReservationById(reservationResponse.reservationId());
    }

    private boolean flightHasTakenPlace(Reservation reservation) {
        FlightResponse flight = flightService.getFlightInfo(reservation.getFlightNumber());
        return flight.departureDateTime().isBefore(dateCalculator.getCurrentDateTime());
    }

    /**
     * Updates the reservation status to 'COMPLETED'.
     *
     * @param reservation The reservation to update.
     */
    public void updateReservationStatusToCompleted(Reservation reservation) {
        try {
            reservation.setStatus(FlightReservationStatus.COMPLETED);
            reservationRepository.save(reservation);
        } catch (Exception e) {
            log.error("Failed to update reservation status: {}", e.getMessage(), e);
        }
    }
}