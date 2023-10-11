package pl.matcodem.trackingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airline {

    /**
     * The unique code that identifies the airline.
     */
    @Id
    @NotBlank
    private String airlineCode;

    /**
     * The name of the airline.
     */
    @NotBlank
    private String name;
}
