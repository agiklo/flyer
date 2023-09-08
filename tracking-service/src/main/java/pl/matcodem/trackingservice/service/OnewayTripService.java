package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.mapper.TripMapper;
import pl.matcodem.trackingservice.repository.TripRepository;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OnewayTripService {

    private final TripMapper tripMapper;
    private final TripRepository tripRepository;
    private final FlightService flightService;

    public Page<OnewayTripResponse> findOnewayTrips(OnewayTripRequest request) {
        var departureIcaoCode = request.departureAirportCode();
        var arrivalIcaoCode = request.arrivalAirportCode();
        var departureDate = request.departureDate().atStartOfDay();
        List<Trip> foundTrips = tripRepository.getTripsByIcaoCodesAndDepatureDate(departureIcaoCode, arrivalIcaoCode, departureDate);
        var mappedTrips = foundTrips.stream().map(tripMapper::mapTripToOnewayTripResponse).toList();
        return new PageImpl<>(mappedTrips);
    }

    /**
     * Method which search possible trips with max 1 stopover between
     *
     * @param departureIcaoCode icao code of the departure airport
     * @param arrivalIcaoCode   icao code of the destinated airport
     * @return list of possible trips to choose
     */
    public List<Trip> findPossibleTrips(String departureIcaoCode, String arrivalIcaoCode) {
        List<Trip> possibleTrips = new LinkedList<>();
        List<Flight> foundFlights = flightService.getAllFlightsByDepartureIcao(departureIcaoCode);
        foundFlights.forEach(flight -> {
            Airport arrivalAirport = flight.getArrivalAirport();
            if (isMatchingDestination(arrivalAirport, arrivalIcaoCode)) {
                possibleTrips.add(Trip.builder()
                        .departureAirport(flight.getDepartureAirport())
                        .arrivalAirport(flight.getArrivalAirport())
                        .departureDateTime(flight.getDepartureDateTime())
                        .durationMinutes(flight.getDurationMinutes())
                        .flights(Set.of(flight))
                        .build());
            }
            List<Flight> secondFlights = flightService.getAllFlightsByDepartureIcao(arrivalAirport.getIcaoCode());
            secondFlights.stream()
                    .filter(secondFlight -> isMatchingDestination(secondFlight.getArrivalAirport(), arrivalIcaoCode))
                    .map(secondFlight -> Trip.builder()
                            .departureAirport(flight.getDepartureAirport())
                            .arrivalAirport(secondFlight.getArrivalAirport())
                            .departureDateTime(flight.getDepartureDateTime())
                            .durationMinutes(flight.getDurationMinutes() + secondFlight.getDurationMinutes())
                            .flights(Set.of(flight, secondFlight))
                            .build())
                    .forEach(possibleTrips::add);
        });
        return possibleTrips;
    }

    private boolean isMatchingDestination(Airport airport, String arrivalIcaoCode) {
        return airport.getIcaoCode().equals(arrivalIcaoCode);
    }
}
