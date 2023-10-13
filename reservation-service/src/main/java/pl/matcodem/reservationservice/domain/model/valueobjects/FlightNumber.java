package pl.matcodem.reservationservice.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;

public record FlightNumber(@NotBlank String number) {

}
