package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;
import pl.matcodem.trackingservice.events.FlightDelayEvent;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Flight.findFlightsByDepartureAndArrivalAirports",
        query = """
                SELECT f FROM Flight f WHERE
                f.arrivalAirport.icaoCode =:arrivalIcao
                AND
                f.departureAirport.icaoCode =:departureIcao
                """)
@NamedQuery(name = "Flight.findFlightsByDepartureIcaoCodeAndDateTimeAfter",
        query = """
                SELECT f FROM Flight f WHERE
                f.departureAirport.icaoCode =:departureIcao
                AND
                f.departureDateTime >= :date
                """)
@NamedQuery(name = "Flight.findFlightsByArrivalAirport",
        query = "SELECT f FROM Flight f WHERE f.arrivalAirport.icaoCode =:arrivalIcao")
@NamedQuery(name = "Flight.findFlightsByDepartureAirport",
        query = "SELECT f FROM Flight f WHERE f.departureAirport.icaoCode =:departureIcao")
public class Flight extends AbstractAggregateRoot<Flight> {

    @Id
    @Column(name = "designator_code")
    private String designatorCode;

    @ManyToOne
    @JoinColumn(name = "departure_icao_code")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_icao_code")
    private Airport arrivalAirport;

    @ManyToOne
    @JoinColumn(name = "airline_code")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "call_sign")
    private Aircraft aircraft;

    private String cabin;

    private Integer durationMinutes;
    private LocalDateTime departureDateTime;

    @OneToOne
    @JoinColumn(name = "stopover_code")
    private Stopover stopover;

    public boolean isFinalDestination() {
        return stopover == null;
    }

    public LocalDateTime getArrivalDateTime() {
        return departureDateTime.plusMinutes(durationMinutes);
    }

    public void setNewDepartureDateTimeDelay(LocalDateTime departureDateTimeDelay) {
        int delayInMinutes = 0; // implement difference between departureDateTimeDelay and departureDateTime
        this.departureDateTime = departureDateTimeDelay;
        registerEvent(new FlightDelayEvent(designatorCode, departureDateTimeDelay, delayInMinutes));
    }
}
