package pl.matcodem.trackingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Id
    private String allianceCode;
    private String name;
}
