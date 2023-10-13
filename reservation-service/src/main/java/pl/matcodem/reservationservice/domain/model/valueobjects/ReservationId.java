package pl.matcodem.reservationservice.domain.model.valueobjects;

import java.util.UUID;

/**
 * A value object representing a unique identifier for a reservation.
 */
public record ReservationId(String value) {

    /**
     * Creates a new instance of a ReservationId with the provided value.
     *
     * @param value The UUID value for the ReservationId.
     * @throws IllegalArgumentException if the provided value is null, empty, or not a valid UUID.
     */
    public ReservationId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ReservationId must not be null or empty");
        }
        if (!isValidUUID(value)) {
            throw new IllegalArgumentException("ReservationId must be a valid UUID");
        }
    }

    /**
     * Generates a new unique ReservationId.
     *
     * @return A new ReservationId generated with a random UUID value.
     */
    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID().toString());
    }

    /**
     * Validates if a given string is a valid UUID.
     *
     * @param value The string to validate.
     * @return true if the string is a valid UUID, false otherwise.
     */
    private static boolean isValidUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
