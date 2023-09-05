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
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Flight.getFlightsByDepartureIcaoCode",
        query = "SELECT f FROM Flight f WHERE f.departureAirport.icaoCode =:code")
@NamedQuery(name = "Flight.getFlightsByArrivalIcaoCode",
        query = "SELECT f FROM Flight f WHERE f.arrivalAirport.icaoCode =:code")
@NamedQuery(name = "Flight.getFlightsByIcaoCodesAndDepatureDate",
        query = """
                SELECT f FROM Flight f WHERE
                f.arrivalAirport.icaoCode =:arrivalCode AND
                f.departureAirport.icaoCode =:departureCode AND
                f.departureDateTime =: date
                """)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "departure_icao_code")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_icao_code")
    private Airport arrivalAirport;

    @ManyToMany
    @JoinTable(name = "flight_alliance",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "alliance_code"))
    private Set<Alliance> alliances;

    private LocalDateTime departureDateTime;
    private Integer durationMinutes;

    @OneToMany
    @JoinColumn(name = "designator_code")
    private Set<Segment> segments;

    private Boolean highlyRatedCarrier;
    private Double score;

    @Embedded
    private Price price;

    public boolean containsNewAircraft() {
        return this.segments.stream()
                .map(Segment::getAircraft)
                .anyMatch(Aircraft::isNewAircraft);
    }

    public boolean containsOldAircraft() {
        return this.segments.stream()
                .map(Segment::getAircraft)
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

    public boolean isDirectFlight() {
        if (segments.isEmpty()) {
            throw new IllegalStateException("A flight must consist of at least one segment!");
        }
        return segments.size() == 1;
    }

    public Set<String> getAirlineCodes() {
    return segments.stream()
            .map(segment -> segment.getAirline().getAirlineCode())
            .collect(toSet());
    }

    public boolean containsShortStopover() {
        return segments.stream()
                .map(Segment::getStopover)
                .anyMatch(Stopover::isShortStopover);
    }

    public boolean containsLongStopover() {
        return segments.stream()
                .map(Segment::getStopover)
                .anyMatch(stopover -> !stopover.isShortStopover());
    }

    public Integer getstopoversCount() {
        return Math.toIntExact(segments.stream()
                .map(Segment::getStopover)
                .filter(Objects::nonNull)
                .count());
    }

    public Integer getStopoverDurationMinutes() {
        return segments.stream()
                .mapToInt(segment -> segment.getStopover().getStopoverDurationMinutes())
                .sum();
    }

    public Set<String> getAllianceCodes() {
        return alliances.stream()
                .map(Alliance::getAllianceCode)
                .collect(toSet());
    }

    public Set<String> getStopoverAiportCodes() {
        return segments.stream()
                .map(segment -> segment.getStopover().getAirport().getIcaoCode())
                .collect(toSet());
    }

    public LocalTime getStopoverDuration() {
        var stopoverDuration = LocalTime.MIDNIGHT;
        segments.stream()
                .map(segment -> segment.getStopover().getStopoverDurationMinutes())
                .forEach(stopoverDuration::plusMinutes);
        return stopoverDuration;
    }
}