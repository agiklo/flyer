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
@Table(name = "alliances")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alliance {

    /**
     * The unique code that identifies the alliance.
     */
    @Id
    @NotBlank
    private String allianceCode;

    /**
     * The name of the alliance.
     */
    @NotBlank
    private String name;
}
