package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.repository.FlightRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> getAllFlightsByDepartureIcao(String departureIcao, LocalDate date) {
        return flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter(departureIcao, date.atStartOfDay());
    }
}
