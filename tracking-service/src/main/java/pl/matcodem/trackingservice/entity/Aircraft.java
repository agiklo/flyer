package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.matcodem.trackingservice.enums.CabinClass;

import java.util.Set;

@Entity
@Table(name = "aircrafts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Aircraft {

    /**
     * The unique call sign that identifies the aircraft.
     */
    @Id
    @NotBlank
    private String callSign;

    /**
     * The seating capacity of the aircraft.
     */
    @NotNull
    private Integer seatingCapacity;

    /**
     * The number of luxury seats on the aircraft.
     */
    @NotNull
    private Integer luxurySeats;

    /**
     * The model or type of the aircraft.
     */
    @NotBlank
    private String model;

    /**
     * Indicates whether the aircraft is new.
     */
    private boolean isNewAircraft;

    /**
     * The set of cabin classes available on the aircraft.
     */
    @NotNull
    @Size(min = 1)
    @Enumerated(value = EnumType.STRING)
    private Set<CabinClass> classes;

    /**
     * Checks if the aircraft is full, considering a specified threshold.
     *
     * @param occupancyThreshold The threshold as a percentage of total seating capacity (0.0 to 1.0).
     * @return true if the aircraft's occupancy exceeds the threshold, false otherwise.
     */
    public boolean isFull(double occupancyThreshold) {
        if (occupancyThreshold < 0.0 || occupancyThreshold > 1.0) {
            throw new IllegalArgumentException("Invalid occupancy threshold. It must be between 0.0 and 1.0.");
        }

        int totalSeats = seatingCapacity;
        int occupiedLuxurySeats = luxurySeats;

        // Calculate the number of available standard seats (subtract luxury seats)
        int availableStandardSeats = totalSeats - occupiedLuxurySeats;

        // Calculate occupancy based on available standard seats
        double occupancy = 1.0 - (double) availableStandardSeats / totalSeats;

        return occupancy >= occupancyThreshold;
    }
}
