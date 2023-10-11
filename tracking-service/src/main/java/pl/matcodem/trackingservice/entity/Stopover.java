package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    /**
     * The unique code that identifies the stopover.
     */
    @Id
    @NotBlank
    private String stopoverCode;

    /**
     * The airport where the stopover occurs.
     */
    @ManyToOne
    @JoinColumn(name = "icao_code")
    @NotNull
    private Airport airport;

    /**
     * The duration of the stopover in minutes.
     */
    @Min(0)
    @NotNull
    private Integer stopoverDurationMinutes;

    /**
     * The preceding flight that includes the stopover.
     */
    @OneToOne(mappedBy = "stopover")
    private Flight precedingFlight;

    /**
     * Checks if the stopover is short based on destination country and duration.
     *
     * @return true if the stopover is short, false otherwise.
     */
    public boolean isShortStopover() {
        String destinationCountry = precedingFlight.getArrivalAirport().getCountry();
        boolean isTheSameCountry = airport.getCountry().equals(destinationCountry);
        return isTheSameCountry ? stopoverDurationMinutes <= 30 : stopoverDurationMinutes <= 60;
    }

    /**
     * Calculates the stopover duration as a LocalTime object.
     *
     * @return the stopover duration as a LocalTime object.
     */
    public LocalTime getStopoverDurationTime() {
        int hours = this.stopoverDurationMinutes / 60;
        int minutes = this.stopoverDurationMinutes % 60;
        return LocalTime.of(hours, minutes);
    }
}
