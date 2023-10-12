package pl.matcodem.trackingservice.mapper;

import org.springframework.stereotype.Component;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.response.AirportResponse;

@Component
public class AirportMapper {

    public AirportResponse mapToAirportResponse(Airport airport) {
        return new AirportResponse(
                airport.getIcaoCode(),
                airport.getName(),
                airport.getContinent(),
                airport.getCountry(),
                airport.getLatitude(),
                airport.getLongitude()
        );
    }
}
