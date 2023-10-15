package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightAdministrationService {

    private final AirportRepository airportRepository;
    private final AirLineRepository airLineRepository;
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final ExternalAirportDatabase airportDatabase;

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
        Airport airport = Airport.builder()
                .icaoCode(airportInfo.icao())
                .name(airportInfo.airport_name())
                .continent("Europe")
                .country(airportInfo.country())
                .latitude(airportInfo.latitude())
                .longitude(airportInfo.longitude())
                .build();
        return airportRepository.saveAndFlush(airport);
    }
}
