package pl.matcodem.trackingservice.service.external.api.country;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CountryInfo(@JsonProperty("name") Name name,
                          @JsonProperty("region") String region) {
}


