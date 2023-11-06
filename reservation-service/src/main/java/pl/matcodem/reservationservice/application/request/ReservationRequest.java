package pl.matcodem.reservationservice.application.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;
import pl.matcodem.reservationservice.domain.model.valueobjects.Passenger;
import pl.matcodem.reservationservice.domain.model.valueobjects.ReservationDate;

import java.util.List;

@Data
public class ReservationRequest {


    @NotNull
    private ReservationDate reservationDate;

    @Valid
    @NotNull
    private List<Passenger> passengers;

    @Valid
    @NotNull
    private FlightNumber flightNumber;
}
