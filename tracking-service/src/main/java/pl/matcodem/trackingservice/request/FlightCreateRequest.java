package pl.matcodem.trackingservice.request;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import pl.matcodem.trackingservice.entity.Stopover;
import pl.matcodem.trackingservice.enums.CabinClass;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightCreateRequest {
    @NotBlank
    private String designatorCode;

    @NotNull
    private String departureAirportIcaoCode;

    @NotNull
    private String arrivalAirportIcaoCode;

    @NotNull
    private String airlineCode;

    @NotNull
    private String aircraftCallSign;

    @NotBlank
    private Set<CabinClass> cabins;

    @NotNull
    private Integer durationMinutes;

    @NotNull
    private LocalDateTime departureDateTime;

    private Stopover stopover;
}
