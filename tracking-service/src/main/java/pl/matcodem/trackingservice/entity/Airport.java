package pl.matcodem.trackingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    /**
     * The unique ICAO code that identifies the airport.
     */
    @Id
    @NotBlank
    @Column(name = "icao_code")
    private String icaoCode;

    /**
     * The name of the airport.
     */
    @NotBlank
    private String name;

    /**
     * The continent where the airport is located.
     */
    @NotBlank
    private String continent;

    /**
     * The country where the airport is located.
     */
    @NotBlank
    private String country;

    /**
     * The latitude coordinate of the airport's location.
     */
    @NotNull
    private Double latitude;

    /**
     * The longitude coordinate of the airport's location.
     */
    @NotNull
    private Double longitude;
}
