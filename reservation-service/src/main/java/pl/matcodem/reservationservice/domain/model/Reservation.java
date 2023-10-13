package pl.matcodem.reservationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;


@Data
@AllArgsConstructor
public class Reservation {
    private ReservationId id;
    private ReservationCode reservationCode;
    private ReservationDate reservationDate;
    private Passenger passenger;
    private FlightNumber flightNumber;
    private FlightReservationStatus status;
}
