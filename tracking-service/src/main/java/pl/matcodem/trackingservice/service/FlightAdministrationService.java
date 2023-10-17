package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Aircraft;
import pl.matcodem.trackingservice.entity.Airline;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.repository.AirLineRepository;
import pl.matcodem.trackingservice.repository.AircraftRepository;
import pl.matcodem.trackingservice.repository.AirportRepository;
import pl.matcodem.trackingservice.repository.FlightRepository;
import pl.matcodem.trackingservice.request.FlightCreateRequest;
import pl.matcodem.trackingservice.service.external.api.airport.AirportInfo;
import pl.matcodem.trackingservice.service.external.api.airport.ExternalAirportDatabase;
import pl.matcodem.trackingservice.service.external.api.country.CountryInfo;
import pl.matcodem.trackingservice.service.external.api.country.ExternalCountryDatabase;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightAdministrationService {

    private final AirportRepository airportRepository;
    private final AirLineRepository airLineRepository;
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final ExternalAirportDatabase airportDatabase;
    private final ExternalCountryDatabase countryDatabase;

    public Flight createFlight(FlightCreateRequest request) {
        Airport departureAirport = getOrCreateAirportByIcaoCode(request.getDepartureAirportIcaoCode());
        Airport arrivalAirport = getOrCreateAirportByIcaoCode(request.getArrivalAirportIcaoCode());

        Airline airline = airLineRepository.findById(request.getAirlineCode())
                .orElseThrow(() -> new NullPointerException("Airline not found"));

        Aircraft aircraft = aircraftRepository.findAircraftByCallSign(request.getAircraftCallSign())
                .orElseThrow(() -> new NullPointerException("Aircraft not found"));

        Flight flight = Flight.builder()
                .designatorCode(request.getDesignatorCode())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .airline(airline)
                .aircraft(aircraft)
                .cabins(request.getCabins())
                .durationMinutes(request.getDurationMinutes())
                .departureDateTime(request.getDepartureDateTime())
                .stopover(request.getStopover())
                .build();

        return flightRepository.save(flight);
    }

    private Airport getOrCreateAirportByIcaoCode(String icaoCode) {
        Optional<Airport> airportOptional = airportRepository.getAirportByIcaoCode(icaoCode);
        return airportOptional.orElseGet(() -> createAirportFromExternalData(icaoCode));
    }

    private Airport createAirportFromExternalData(String icaoCode) {
        AirportInfo airportInfo = airportDatabase.getAirportInfoByIcaoCode(icaoCode).block();

        if (airportInfo == null) {
            log.error("Failed to retrieve airport information for ICAO code: {}", icaoCode);
            return null;
        }

        String countryName = airportInfo.country();
        String continent = getContinentForCountry(countryName);

        Airport airport = Airport.builder()
                .icaoCode(airportInfo.icao())
                .name(airportInfo.airport_name())
                .continent(continent)
                .country(countryName)
                .latitude(airportInfo.latitude())
                .longitude(airportInfo.longitude())
                .build();

        try {
            airport = airportRepository.saveAndFlush(airport);
            log.info("Created airport: ICAO={}, Name={}", airport.getIcaoCode(), airport.getName());
            return airport;
        } catch (Exception e) {
            log.error("Failed to save airport information for ICAO code: {}", icaoCode, e);
            return null;
        }
    }

    private String getContinentForCountry(String countryName) {
        Optional<CountryInfo> countryInfo = countryDatabase.getCountryInfoByName(countryName);
        return countryInfo.map(CountryInfo::region).orElse("No data");
    }
}
