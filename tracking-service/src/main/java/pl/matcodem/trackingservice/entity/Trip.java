package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Trip.getTripsByDepartureIcaoCode",
        query = "SELECT t FROM Trip t WHERE t.departureAirport.icaoCode =:code")
@NamedQuery(name = "Trip.getTripsByArrivalIcaoCode",
        query = "SELECT t FROM Trip t WHERE t.arrivalAirport.icaoCode =:code")
@NamedQuery(name = "Trip.getTripsByIcaoCodesAndDepatureDate",
        query = """
                SELECT t FROM Trip t WHERE
                t.arrivalAirport.icaoCode =:arrivalCode AND
                t.departureAirport.icaoCode =:departureCode AND
                t.departureDateTime =: date
                """)
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "departure_icao_code")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_icao_code")
    private Airport arrivalAirport;

    @ManyToMany
    @JoinTable(name = "trip_alliance",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "alliance_code"))
    private Set<Alliance> alliances;

    private LocalDateTime departureDateTime;
    private Integer durationMinutes;

    @OneToMany
    @JoinColumn(name = "designator_code")
    private Set<Flight> flights;

    private Boolean highlyRatedCarrier;
    private Double score;

    @Embedded
    private Price price;

    public boolean containsNewAircraft() {
        return this.flights.stream()
                .map(Flight::getAircraft)
                .anyMatch(Aircraft::isNewAircraft);
    }

    public boolean containsOldAircraft() {
        return this.flights.stream()
                .map(Flight::getAircraft)
                .anyMatch(aircraft -> !aircraft.isNewAircraft());
    }

    public LocalDateTime getArrivalDateTime() {
        return departureDateTime.plusMinutes(durationMinutes);
    }

    public Integer getDurationInDays() {
        return (int) (durationMinutes / 1.440);
    }

    public LocalTime getDepartureTime() {
        return departureDateTime.toLocalTime();
    }

    public LocalTime getArrivalTime() {
        return getArrivalDateTime().toLocalTime();
    }

    public boolean isOvernight() {
        LocalTime startNight = LocalTime.parse("21:00:00");
        LocalTime stopNight = LocalTime.parse("06:00:00");
        return getDepartureTime().isAfter(startNight) ||
                getArrivalTime().isBefore(stopNight);
    }

    public boolean isEarlyDeparture() {
        return getDepartureTime().isBefore(LocalTime.parse("07:00:00"));
    }

    public boolean isLateArrival() {
        return getArrivalTime().isAfter(LocalTime.parse("21:00:00"));
    }

    public boolean isDirectTrip() {
        if (flights.isEmpty()) {
            throw new IllegalStateException("A trip must consist of at least one flight!");
        }
        return flights.size() == 1;
    }

    public Set<String> getAirlineCodes() {
    return flights.stream()
            .map(flight -> flight.getAirline().getAirlineCode())
            .collect(toSet());
    }

    public boolean containsShortStopover() {
        return flights.stream()
                .map(Flight::getStopover)
                .anyMatch(Stopover::isShortStopover);
    }

    public boolean containsLongStopover() {
        return flights.stream()
                .map(Flight::getStopover)
                .anyMatch(stopover -> !stopover.isShortStopover());
    }

    public Integer getstopoversCount() {
        return Math.toIntExact(flights.stream()
                .map(Flight::getStopover)
                .filter(Objects::nonNull)
                .count());
    }

    public Integer getStopoverDurationMinutes() {
        return flights.stream()
                .mapToInt(flight -> flight.getStopover().getStopoverDurationMinutes())
                .sum();
    }

    public Set<String> getAllianceCodes() {
        return alliances.stream()
                .map(Alliance::getAllianceCode)
                .collect(toSet());
    }

    public Set<String> getStopoverAiportCodes() {
        return flights.stream()
                .map(flight -> flight.getStopover().getAirport().getIcaoCode())
                .collect(toSet());
    }

    public LocalTime getStopoverDuration() {
        var stopoverDuration = LocalTime.MIDNIGHT;
        flights.stream()
                .map(flight -> flight.getStopover().getStopoverDurationMinutes())
                .forEach(stopoverDuration::plusMinutes);
        return stopoverDuration;
    }
}