package pl.matcodem.trackingservice.request;

import jakarta.validation.constraints.NotBlank;

public record AirportDistanceRequest(
        @NotBlank(message = "Origin ICAO code must not be empty") String originIcaoCode,
        @NotBlank(message = "Destination ICAO code must not be empty") String destinationIcaoCode
) {}


