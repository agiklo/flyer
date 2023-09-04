package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
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
}