package pl.matcodem.reservationservice.infrastructure.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.exceptions.ReservationModificationNotAllowedException;
import pl.matcodem.reservationservice.util.DateCalculator;
import pl.matcodem.reservationservice.util.annotations.Validator;

import java.time.LocalDate;
import java.time.Period;

@Validator
public class ModificationPeriodValidator {
    private final DateCalculator dateCalculator;
    private final int allowedDays;

    @Autowired
    public ModificationPeriodValidator(
            DateCalculator dateCalculator,
            @Value("${reservation.modification.allowed-days}") int allowedDays) {
        this.dateCalculator = dateCalculator;
        this.allowedDays = allowedDays;
    }

    public void isReservationWithinAllowedModificationPeriod(Reservation reservation) {
        LocalDate currentDate = dateCalculator.getCurrentDate();
        LocalDate flightDate = reservation.getReservationDate().date();
        Period period = dateCalculator.calculatePeriod(currentDate, flightDate);
        int daysUntilFlight = period.getDays();

        if (daysUntilFlight < allowedDays) {
            throw new ReservationModificationNotAllowedException("Reservation modification is not allowed within " + allowedDays + " days of the flight");
        }
    }
}

