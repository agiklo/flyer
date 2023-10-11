package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import pl.matcodem.trackingservice.constants.TripQueries;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "trips")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = TripQueries.GET_TRIPS_BY_DEPARTURE_ICAO_CODE, query = TripQueries.SQL_GET_TRIPS_BY_DEPARTURE_ICAO_CODE)
@NamedQuery(name = TripQueries.GET_TRIPS_BY_ARRIVAL_ICAO_CODE, query = TripQueries.SQL_GET_TRIPS_BY_ARRIVAL_ICAO_CODE)
@NamedQuery(name = TripQueries.GET_TRIPS_BY_ICAO_CODES_AND_DEPARTURE_DATE, query = TripQueries.SQL_GET_TRIPS_BY_ICAO_CODES_AND_DEPARTURE_DATE)
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "departure_icao_code")
    @NotNull
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_icao_code")
    @NotNull
    private Airport arrivalAirport;

    @ManyToMany
    @JoinTable(name = "trip_alliance",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "alliance_code"))
    private Set<Alliance> alliances;

    @NotNull
    @PastOrPresent
    private LocalDateTime departureDateTime;

    @Min(0)
    @NotNull
    private Integer durationMinutes;

    @OneToMany
    @JoinColumn(name = "designator_code")
    @NotNull
    private Set<Flight> flights;

    private Boolean highlyRatedCarrier;

    @PositiveOrZero
    private Double score;

    @Embedded
    private Price price;

    public Trip(Airport departureAirport, Airport arrivalAirport, Set<Alliance> alliances, LocalDateTime departureDateTime, Integer durationMinutes, Set<Flight> flights, Boolean highlyRatedCarrier, Double score, Price price) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.alliances = alliances;
        this.departureDateTime = departureDateTime;
        this.durationMinutes = durationMinutes;
        this.flights = flights;
        this.highlyRatedCarrier = highlyRatedCarrier;
        this.score = score;
        this.price = price;
    }

    /**
     * Checks if the trip contains at least one new aircraft among its flights.
     *
     * @return true if at least one flight uses a new aircraft, false otherwise.
     */
    public boolean containsNewAircraft() {
        return this.flights.stream()
                .map(Flight::getAircraft)
                .anyMatch(Aircraft::isNewAircraft);
    }

    /**
     * Checks if the trip contains at least one old aircraft among its flights.
     *
     * @return true if at least one flight uses an old aircraft, false otherwise.
     */
    public boolean containsOldAircraft() {
        return this.flights.stream()
                .map(Flight::getAircraft)
                .anyMatch(aircraft -> !aircraft.isNewAircraft());
    }

    /**
     * Calculates the arrival date and time of the trip.
     *
     * @return the calculated arrival date and time.
     */
    public LocalDateTime getArrivalDateTime() {
        return departureDateTime.plusMinutes(durationMinutes);
    }

    /**
     * Calculates the duration of the trip in days.
     *
     * @return the trip duration in days.
     */
    public Integer getDurationInDays() {
        return (int) (durationMinutes / 1.440);
    }

    /**
     * Retrieves the departure time of the trip.
     *
     * @return the departure time as a LocalTime object.
     */
    public LocalTime getDepartureTime() {
        return departureDateTime.toLocalTime();
    }

    /**
     * Retrieves the arrival time of the trip.
     *
     * @return the arrival time as a LocalTime object.
     */
    public LocalTime getArrivalTime() {
        return getArrivalDateTime().toLocalTime();
    }

    /**
     * Checks if the trip involves an overnight stay.
     *
     * @return true if the trip is overnight, false otherwise.
     */
    public boolean isOvernight() {
        LocalTime startNight = LocalTime.parse("21:00:00");
        LocalTime stopNight = LocalTime.parse("06:00:00");
        return getDepartureTime().isAfter(startNight) ||
                getArrivalTime().isBefore(stopNight);
    }

    /**
     * Checks if the trip involves an early departure.
     *
     * @return true if the trip has an early departure, false otherwise.
     */
    public boolean isEarlyDeparture() {
        return getDepartureTime().isBefore(LocalTime.parse("07:00:00"));
    }

    /**
     * Checks if the trip involves a late arrival.
     *
     * @return true if the trip has a late arrival, false otherwise.
     */
    public boolean isLateArrival() {
        return getArrivalTime().isAfter(LocalTime.parse("21:00:00"));
    }

    /**
     * Checks if the trip is a direct trip (consists of a single flight).
     *
     * @return true if the trip is direct, false otherwise.
     */
    public boolean isDirectTrip() {
        if (flights.isEmpty()) {
            throw new IllegalStateException("A trip must consist of at least one flight!");
        }
        return flights.size() == 1;
    }

    /**
     * Retrieves the airline codes for all flights in the trip.
     *
     * @return a set of airline codes.
     */
    public Set<String> getAirlineCodes() {
        return flights.stream()
                .map(flight -> flight.getAirline().getAirlineCode())
                .collect(toSet());
    }

    /**
     * Checks if the trip contains at least one flight with a short stopover.
     *
     * @return true if at least one flight has a short stopover, false otherwise.
     */
    public boolean containsShortStopover() {
        return flights.stream()
                .map(Flight::getStopover)
                .anyMatch(Stopover::isShortStopover);
    }

    /**
     * Checks if the trip contains at least one flight with a long stopover.
     *
     * @return true if at least one flight has a long stopover, false otherwise.
     */
    public boolean containsLongStopover() {
        return flights.stream()
                .map(Flight::getStopover)
                .anyMatch(stopover -> !stopover.isShortStopover());
    }

    /**
     * Retrieves the count of stopovers in the trip.
     *
     * @return the count of stopovers.
     */
    public Integer getStopoversCount() {
        return Math.toIntExact(flights.stream()
                .map(Flight::getStopover)
                .filter(Objects::nonNull)
                .count());
    }

    /**
     * Retrieves the total stopover duration in minutes for all flights in the trip.
     *
     * @return the total stopover duration in minutes.
     */
    public Integer getStopoverDurationMinutes() {
        return flights.stream()
                .mapToInt(flight -> flight.getStopover().getStopoverDurationMinutes())
                .sum();
    }

    /**
     * Retrieves the alliance codes for the trip's alliances.
     *
     * @return a set of alliance codes.
     */
    public Set<String> getAllianceCodes() {
        return alliances.stream()
                .map(Alliance::getAllianceCode)
                .collect(toSet());
    }

    /**
     * Retrieves the airport codes for the stopovers in the trip's flights.
     *
     * @return a set of airport codes.
     */
    public Set<String> getStopoverAirportCodes() {
        return flights.stream()
                .map(flight -> flight.getStopover().getAirport().getIcaoCode())
                .collect(toSet());
    }

    /**
     * Retrieves the total stopover duration as a LocalTime object.
     *
     * @return the total stopover duration as a LocalTime object.
     */
    public LocalTime getStopoverDuration() {
        var stopoverDuration = LocalTime.MIDNIGHT;
        for (Flight flight : flights) {
            stopoverDuration = stopoverDuration.plusMinutes(flight.getStopover().getStopoverDurationMinutes());
        }
        return stopoverDuration;
    }
}
