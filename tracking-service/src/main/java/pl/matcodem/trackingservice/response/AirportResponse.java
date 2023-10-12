package pl.matcodem.trackingservice.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.matcodem.trackingservice.entity.Airport;

public record AirportResponse(
        @NotBlank String icaoCode,
        @NotBlank String name,
        @NotBlank String continent,
        @NotBlank String country,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
    public AirportResponse(Airport airport) {
        this(airport.getIcaoCode(), airport.getName(), airport.getContinent(), airport.getCountry(),
                airport.getLatitude(), airport.getLongitude());
    }
}
