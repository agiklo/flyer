package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "segments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Segment {

    @Id
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
}
