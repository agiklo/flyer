package pl.matcodem.reservationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;

import java.util.List;


@Data
@AllArgsConstructor
public class Reservation {
    private ReservationId id;
    private ReservationCode reservationCode;
    private ReservationDate reservationDate;
    private List<Passenger> passengers;
    private FlightNumber flightNumber;
    private FlightReservationStatus status;
}
