package pl.matcodem.trackingservice.entity;

import jakarta.persistence.*;
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

    @Id
    private String callSign;
    private Integer seatingCapacity;
    private Integer availableSeats;
    private Integer luxurySeats;
    private String model;
    private boolean isNewAircraft;

    @Enumerated(value = EnumType.STRING)
    private Set<CabinClass> classes;

    public boolean isFull() {
        return availableSeats == 0;
    }
}
