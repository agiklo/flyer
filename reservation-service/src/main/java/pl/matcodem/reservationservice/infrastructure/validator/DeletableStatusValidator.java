package pl.matcodem.reservationservice.infrastructure.validator;

import org.springframework.beans.factory.annotation.Value;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;
import pl.matcodem.reservationservice.util.annotations.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Validator
public class DeletableStatusValidator {
    private final Set<FlightReservationStatus> deletableStatusValues;

    public DeletableStatusValidator(@Value("${reservation.deletable-statuses}")
                                    List<FlightReservationStatus> deletableStatuses) {
        this.deletableStatusValues = new HashSet<>(deletableStatuses);
    }

    public void validateDeletableStatus(FlightReservationStatus status) {
        if (!deletableStatusValues.contains(status)) {
            throw new IllegalArgumentException("Cannot delete a reservation with status: " + status);
        }
    }
}


