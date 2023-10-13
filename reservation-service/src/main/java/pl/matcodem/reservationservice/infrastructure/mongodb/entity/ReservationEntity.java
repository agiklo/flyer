package pl.matcodem.reservationservice.infrastructure.mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "reservations")
public class ReservationEntity {
    @Id
    private String id;
    private String reservationCode;
    private LocalDate reservationDate;
    private String firstName;
    private String lastName;
    private String pesel;
    private String flightNumber;
    private FlightReservationStatus status;

    public Reservation toReservation() {
        return new Reservation(
                new ReservationId(id),
                new ReservationCode(reservationCode),
                new ReservationDate(reservationDate),
                new Passenger(firstName, lastName, pesel),
                new FlightNumber(flightNumber),
                status
        );
    }

    public static ReservationEntity fromReservation(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId().value());
        entity.setReservationCode(reservation.getReservationCode().code());
        entity.setReservationDate(reservation.getReservationDate().date());
        entity.setFirstName(reservation.getPassenger().firstName());
        entity.setLastName(reservation.getPassenger().lastName());
        entity.setPesel(reservation.getPassenger().pesel());
        entity.setFlightNumber(reservation.getFlightNumber().number());
        entity.setStatus(reservation.getStatus());
        return entity;
    }
}
