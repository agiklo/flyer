package pl.matcodem.trackingservice.service.external.api.country;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Name(@JsonProperty("common") String common,
                   @JsonProperty("official") String official) {
}

