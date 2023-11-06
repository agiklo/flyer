package pl.matcodem.reservationservice.infrastructure.mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.matcodem.reservationservice.domain.model.Reservation;
import pl.matcodem.reservationservice.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "reservations")
public class ReservationEntity {
    @Id
    private String id;
    private String reservationCode;
    private LocalDate reservationDate;
    private List<Passenger> passengers;
    private String flightNumber;
    private FlightReservationStatus status;
    private Cost cost;

    public Reservation toReservation() {
        return new Reservation(
                new ReservationId(id),
                new ReservationCode(reservationCode),
                new ReservationDate(reservationDate),
                passengers,
                new FlightNumber(flightNumber),
                cost,
                status
        );
    }

    public static ReservationEntity fromReservation(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId().value());
        entity.setReservationCode(reservation.getReservationCode().code());
        entity.setReservationDate(reservation.getReservationDate().date());
        entity.setPassengers(reservation.getPassengers());
        entity.setFlightNumber(reservation.getFlightNumber().number());
        entity.setCost(reservation.getCost());
        entity.setStatus(reservation.getStatus());
        return entity;
    }
}
