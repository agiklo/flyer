package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "stopovers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stopover {

    @Id
    private String stopoverCode;

    @ManyToOne
    @JoinColumn(name = "icao_code")
    private Airport airport;

    private Integer stopoverDurationMinutes;

    @OneToOne(mappedBy = "stopover")
    private Flight precedingFlight;

    public boolean isShortStopover() {
        String destinationCountry = precedingFlight.getArrivalAirport().getCountry();
        boolean isTheSameCountry = airport.getCountry().equals(destinationCountry);
        return isTheSameCountry ? stopoverDurationMinutes <= 30 : stopoverDurationMinutes <= 60;
    }

    public LocalTime getStopoverDurationTime() {
        int hours = this.stopoverDurationMinutes / 60;
        int minutes = this.stopoverDurationMinutes % 60;
        return LocalTime.of(hours, minutes);
    }
}
