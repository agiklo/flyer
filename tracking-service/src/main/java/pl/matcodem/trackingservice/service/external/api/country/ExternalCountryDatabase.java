package pl.matcodem.trackingservice.service.external.api.country;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalCountryDatabase {

    @Value("${external.api.country.url}")
    private String countryInfoUrl;

    private final RestTemplate restTemplate;

    public Optional<CountryInfo> getCountryInfoByName(String countryName) {
        try {
            ResponseEntity<CountryInfo[]> responseEntity = restTemplate.exchange(
                    countryInfoUrl,
                    HttpMethod.GET,
                    null,
                    CountryInfo[].class,
                    countryName
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                CountryInfo[] countryInfoArray = responseEntity.getBody();
                if (countryInfoArray != null && countryInfoArray.length > 0) {
                    return Optional.of(countryInfoArray[0]);
                }
            }
        } catch (Exception e) {
            log.error("Failed to retrieve country info for '{}': {}", countryName, e.getMessage(), e);
        }

        return Optional.empty();
    }
}