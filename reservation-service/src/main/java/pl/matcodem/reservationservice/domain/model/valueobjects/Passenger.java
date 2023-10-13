package pl.matcodem.reservationservice.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;
import pl.matcodem.reservationservice.domain.validator.PeselValidator;

public record Passenger(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String pesel) {
    public Passenger {
        if (firstName == null || lastName == null || pesel == null) {
            throw new IllegalArgumentException("First name, last name, and PESEL must not be null");
        }
        if (!PeselValidator.isValidPesel(pesel)) {
            throw new IllegalArgumentException("Invalid PESEL format or checksum");
        }
    }

    public static Passenger of(String firstName, String lastName, String pesel) {
        return new Passenger(firstName, lastName, pesel);
    }
}
