package pl.matcodem.reservationservice.application.request;

import jakarta.validation.constraints.NotNull;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationId;

import java.util.List;

public record UpdateReservationRequest(@NotNull ReservationId reservationId, @NotNull List<Passenger> newPassengers) {
}

