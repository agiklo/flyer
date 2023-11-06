package pl.matcodem.reservationservice.domain.model.valueobjects;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;

import java.time.LocalDate;

public record Passenger(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Pattern(regexp = "\\d{11}", message = "Invalid PESEL format or checksum", groups = PeselValidationGroup.class)
        String pesel,

        @Pattern(regexp = "^(?![0-9]*$)[a-zA-Z0-9]+$", message = "Invalid passport number format", groups = PassportValidationGroup.class)
        String passportNumber,

        @Email(message = "Invalid email address")
        String email,

        @Pattern(regexp = "\\d{9}", message = "Invalid phone number")
        String phoneNumber,

        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth,

        @Min(value = 0, message = "Age must be at least 0")
        int age,

        String specialRequirements,

        @NotBlank(message = "Ticket class is required")
        String ticketClass,

        String seatPreference
) {
    public Passenger {
        if ((pesel == null && passportNumber == null) ||
                (pesel != null && passportNumber != null)) {
            throw new IllegalArgumentException("Either PESEL or passport number must be provided, but not both or none.");
        }
    }

    public static Passenger of(
            String firstName, String lastName, String pesel, String passportNumber,
            String email, String phoneNumber, LocalDate dateOfBirth,
            int age, String specialRequirements, String ticketClass,
            String seatPreference) {
        return new Passenger(
                firstName, lastName, pesel, passportNumber, email, phoneNumber,
                dateOfBirth, age, specialRequirements, ticketClass, seatPreference);
    }

    public interface PeselValidationGroup {
    }

    public interface PassportValidationGroup {
    }

    @GroupSequence({PeselValidationGroup.class, PassportValidationGroup.class, Default.class})
    public interface ValidationOrder {
    }
}

