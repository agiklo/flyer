package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.exceptions.AirportNotFoundException;
import pl.matcodem.trackingservice.mapper.AirportMapper;
import pl.matcodem.trackingservice.repository.AirportRepository;
import pl.matcodem.trackingservice.request.AirportDistanceRequest;
import pl.matcodem.trackingservice.response.AirportDistanceResponse;
import pl.matcodem.trackingservice.response.AirportResponse;

import java.util.Optional;

import static pl.matcodem.trackingservice.validator.IcaoCodeValidator.isValidIcaoCode;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final AirportDistanceCalculatorService airportDistanceCalculatorService;

    /**
     * Get a paginated list of airports.
     *
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A Page of AirportResponse objects representing airports.
     */
    public Page<AirportResponse> getAllAirports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Airport> airports = airportRepository.findAll(pageable);

        return airports.map(airportMapper::mapToAirportResponse);
    }

    /**
     * Retrieve an airport by its ICAO code.
     *
     * @param icaoCode The unique ICAO code that identifies the airport.
     * @return An AirportResponse with airport details.
     * @throws IllegalArgumentException if the provided ICAO code is null or empty.
     * @throws AirportNotFoundException if no airport is found with the given ICAO code.
     */
    public AirportResponse getAirportByIcaoCode(String icaoCode) {
        if (isValidIcaoCode(icaoCode)) {
            throw new IllegalArgumentException("ICAO code must not be null or empty.");
        }
        Optional<Airport> airport = airportRepository.getAirportByIcaoCode(icaoCode);
        if (airport.isEmpty()) {
            throw new AirportNotFoundException(icaoCode);
        }
        return new AirportResponse(airport.get());
    }

    /**
     * Calculate the distance between two airports based on their ICAO codes.
     *
     * @param request The request containing the ICAO codes of the origin and destination airports.
     * @return An {@link AirportDistanceResponse} containing the distance between the airports.
     * @throws AirportNotFoundException if one or both of the specified airports are not found.
     */
    public AirportDistanceResponse getDistanceBetweenAirports(AirportDistanceRequest request) {
        Optional<Airport> originAirport = airportRepository.getAirportByIcaoCode(request.originIcaoCode());
        Optional<Airport> destinationAirport = airportRepository.getAirportByIcaoCode(request.destinationIcaoCode());

        if (originAirport.isEmpty() || destinationAirport.isEmpty()) {
            throw new AirportNotFoundException("One or both airports not found");
        }

        double distance = airportDistanceCalculatorService.calculateDistance(
                originAirport.get(), destinationAirport.get());

        return new AirportDistanceResponse(
                request.originIcaoCode(), request.destinationIcaoCode(), distance);
    }
}
