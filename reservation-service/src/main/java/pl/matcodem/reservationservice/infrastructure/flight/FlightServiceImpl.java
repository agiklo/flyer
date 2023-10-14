package pl.matcodem.reservationservice.infrastructure.flight;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.matcodem.reservationservice.domain.model.valueobjects.FlightNumber;

@Service
public class FlightServiceImpl implements FlightService {

    private final RestTemplate restTemplate;

    public FlightServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FlightResponse getFlightInfo(FlightNumber flightNumber) {
        var responseEntity = restTemplate.getForEntity("http://tracking-service/api/flights/{flightNumber}", FlightResponse.class, flightNumber.number());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        return null;
    }

    public boolean flightExists(FlightNumber flightNumber) {
        ResponseEntity<Void> response = restTemplate.getForEntity(
                "http://tracking-service/api/flights/{flightNumber}",
                Void.class,
                flightNumber.number()
        );

        return response.getStatusCode() != HttpStatus.NOT_FOUND;
    }
}
