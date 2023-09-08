package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.repository.AirportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport getAirportByIcaoCode(String icaoCode) {
        return airportRepository.getAirportByIcaoCode(icaoCode);
    }
}
