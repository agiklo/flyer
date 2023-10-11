package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;
import pl.matcodem.trackingservice.constants.FlightQueries;
import pl.matcodem.trackingservice.events.FlightDelayEvent;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = FlightQueries.FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS, query = FlightQueries.SQL_FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS)
@NamedQuery(name = FlightQueries.FIND_FLIGHTS_BY_DEPARTURE_ICAO_AND_DATE_TIME_AFTER, query = FlightQueries.SQL_FIND_FLIGHTS_BY_DEPARTURE_ICAO_AND_DATE_TIME_AFTER)
@NamedQuery(name = FlightQueries.FIND_FLIGHTS_BY_ARRIVAL_AIRPORT, query = FlightQueries.SQL_FIND_FLIGHTS_BY_ARRIVAL_AIRPORT)
@NamedQuery(name = FlightQueries.FIND_FLIGHTS_BY_DEPARTURE_AIRPORT, query = FlightQueries.SQL_FIND_FLIGHTS_BY_DEPARTURE_AIRPORT)
public class Flight extends AbstractAggregateRoot<Flight> {

    /**
     * The designator code that identifies the flight.
     */
    @Id
    @Column(name = "designator_code")
    @NotBlank
    private String designatorCode;

    /**
     * The airport where the flight departs from.
     */
    @ManyToOne
    @JoinColumn(name = "departure_icao_code")
    @NotNull
    private Airport departureAirport;

    /**
     * The airport where the flight arrives.
     */
    @ManyToOne
    @JoinColumn(name = "arrival_icao_code")
    @NotNull
    private Airport arrivalAirport;

    /**
     * The airline operating the flight.
     */
    @ManyToOne
    @JoinColumn(name = "airline_code")
    @NotNull
    private Airline airline;

    /**
     * The aircraft operating the flight.
     */
    @ManyToOne
    @JoinColumn(name = "call_sign")
    @NotNull
    private Aircraft aircraft;

    /**
     * The cabin class of the flight.
     */
    @NotBlank
    private String cabin;

    /**
     * The duration of the flight in minutes.
     */
    @NotNull
    private Integer durationMinutes;

    /**
     * The departure date and time of the flight.
     */
    @NotNull
    private LocalDateTime departureDateTime;

    /**
     * The stopover information for the flight.
     */
    @OneToOne
    @JoinColumn(name = "stopover_code")
    private Stopover stopover;

    /**
     * Checks if the flight is the final destination (no stopover).
     *
     * @return true if the flight is the final destination, false otherwise.
     */
    public boolean isFinalDestination() {
        return stopover == null;
    }

    /**
     * Calculates the arrival date and time based on the departure date and duration.
     *
     * @return the calculated arrival date and time.
     */
    public LocalDateTime getArrivalDateTime() {
        return departureDateTime.plusMinutes(durationMinutes);
    }

    /**
     * Sets a new departure date and time with a delay and registers a delay event.
     *
     * @param departureDateTimeDelay The new departure date and time with the delay.
     */
    public void setNewDepartureDateTimeDelay(LocalDateTime departureDateTimeDelay) {
        long delayInMinutes = Duration.between(departureDateTime, departureDateTimeDelay).toMinutes();
        LocalDateTime newDepartureDateTime = departureDateTimeDelay.plusMinutes(delayInMinutes);
        this.departureDateTime = newDepartureDateTime;
        registerEvent(new FlightDelayEvent(designatorCode, newDepartureDateTime, (int) delayInMinutes));
    }
}

