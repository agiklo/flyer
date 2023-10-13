package pl.matcodem.reservationservice.application.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationCode;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;

@Data
public class ReservationRequest {

    @NotNull
    private ReservationCode reservationCode;

    @NotNull
    private ReservationDate reservationDate;

    @Valid
    @NotNull
    private Passenger passenger;

    @Valid
    @NotNull
    private FlightNumber flightNumber;
}
