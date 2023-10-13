package pl.matcodem.reservationservice.infrastructure.validator;

import org.junit.Before;
import org.junit.Test;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightReservationStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DeletableStatusValidatorTest {

    private DeletableStatusValidator validator;

    @Before
    public void setup() {
        List<FlightReservationStatus> deletableStatuses = List.of(FlightReservationStatus.PENDING);
        validator = new DeletableStatusValidator(deletableStatuses);
    }

    @Test
    public void testValidateDeletableStatus_ValidStatus() {
        assertDoesNotThrow(() -> validator.validateDeletableStatus(FlightReservationStatus.PENDING));
    }

    @Test
    public void testValidateDeletableStatus_InvalidStatus() {
        try {
            // Invalid status
            validator.validateDeletableStatus(FlightReservationStatus.COMPLETED);
            fail("Validation of an invalid status should throw an exception.");
        } catch (IllegalArgumentException e) {
            // Assert that an IllegalArgumentException was thrown for an invalid status
            assertEquals("Cannot delete a reservation with status: COMPLETED", e.getMessage());
        }
    }
}
