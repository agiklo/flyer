package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.mapper.TripMapper;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OnewayTripService {

    private final TripMapper tripMapper;
    private final FlightService flightService;

    /**
     * Find one-way trips based on the provided request with a maximum number of stopovers.
     *
     * @param request The request specifying departure, arrival, date, and maximum stopovers.
     * @return A page of one-way trip responses.
     */
    public Page<OnewayTripResponse> findOnewayTrips(OnewayTripRequest request) {
        String departureIcaoCode = request.departureAirportCode();
        String arrivalIcaoCode = request.arrivalAirportCode();
        LocalDate departureDate = request.departureDate();
        int maxStopovers = request.maxStopovers();
        List<Trip> possibleTrips = findPossibleTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);

        possibleTrips.sort(Comparator.comparingInt(Trip::getDurationMinutes));

        List<OnewayTripResponse> mappedTrips = possibleTrips.stream()
                .map(tripMapper::mapTripToOnewayTripResponse)
                .toList();
        return new PageImpl<>(mappedTrips);
    }

    /**
     * Find possible one-way trips from a departure airport to an arrival airport with a maximum number of stopovers.
     *
     * @param departureIcaoCode The ICAO code of the departure airport.
     * @param arrivalIcaoCode   The ICAO code of the arrival airport.
     * @param departureDate     The departure date.
     * @param maxStopovers      The maximum number of stopovers allowed.
     * @return A list of possible one-way trips.
     */
    private List<Trip> findPossibleTrips(String departureIcaoCode, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers) {
        List<Trip> possibleTrips = new LinkedList<>();

        List<Flight> departureFlights = flightService.getAllFlightsByDepartureIcao(departureIcaoCode, departureDate);

        departureFlights.forEach(departureFlight -> {
            Airport arrivalAirport = departureFlight.getArrivalAirport();
            if (isMatchingDestination(arrivalAirport, arrivalIcaoCode)) {
                possibleTrips.add(buildDirectTrip(departureFlight));
            } else {
                List<Trip> connectingTrips = findConnectingTrips(arrivalAirport, arrivalIcaoCode, departureDate, maxStopovers);
                possibleTrips.addAll(connectingTrips);
            }
        });
        return possibleTrips;
    }

    private List<Trip> findConnectingTrips(Airport departureAirport, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers) {
        List<Trip> connectingTrips = new ArrayList<>();
        findConnectingTripsRecursive(departureAirport, arrivalIcaoCode, departureDate, maxStopovers, new LinkedList<>(), connectingTrips);
        return connectingTrips;
    }

    /**
     * Find connecting trips recursively starting from a given airport.
     *
     * @param currentAirport  The current airport to search from.
     * @param arrivalIcaoCode The ICAO code of the arrival airport.
     * @param departureDate   The departure date.
     * @param maxStopovers    The maximum number of stopovers allowed.
     * @param currentPath     The current path of flights being considered.
     * @param connectingTrips The list to store found connecting trips.
     */
    private void findConnectingTripsRecursive(Airport currentAirport, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers, List<Flight> currentPath, List<Trip> connectingTrips) {
        if (currentPath.size() <= maxStopovers) {
            List<Flight> nextFlights = flightService.getAllFlightsByDepartureIcao(currentAirport.getIcaoCode(), departureDate);
            nextFlights.forEach(nextFlight -> {
                if (isMatchingDestination(nextFlight.getArrivalAirport(), arrivalIcaoCode) && !currentPath.contains(nextFlight)) {
                    currentPath.add(nextFlight);
                    connectingTrips.add(buildConnectingTrip(currentPath));
                    currentPath.remove(currentPath.size() - 1); // Backtrack
                } else {
                    currentPath.add(nextFlight);
                    findConnectingTripsRecursive(nextFlight.getArrivalAirport(), arrivalIcaoCode, departureDate, maxStopovers, currentPath, connectingTrips);
                    currentPath.remove(currentPath.size() - 1); // Backtrack
                }
            });
        }
    }

    /**
     * Build a direct trip from a single flight.
     *
     * @param flight The flight to build the trip from.
     * @return A direct trip.
     */
    private Trip buildDirectTrip(Flight flight) {
        return Trip.builder()
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .departureDateTime(flight.getDepartureDateTime())
                .durationMinutes(flight.getDurationMinutes())
                .flights(Set.of(flight))
                .build();
    }

    /**
     * Build a connecting trip from a list of flights.
     *
     * @param flights The list of flights to build the connecting trip from.
     * @return A connecting trip.
     */
    private Trip buildConnectingTrip(List<Flight> flights) {
        int totalDurationMinutes = flights.stream().mapToInt(Flight::getDurationMinutes).sum();
        Airport departureAirport = flights.get(0).getDepartureAirport();
        Airport arrivalAirport = flights.get(flights.size() - 1).getArrivalAirport();
        LocalDateTime departureDateTime = flights.get(0).getDepartureDateTime();

        return Trip.builder()
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(departureDateTime)
                .durationMinutes(totalDurationMinutes)
                .flights(new HashSet<>(flights))
                .build();
    }

    /**
     * Check if an airport matches the desired arrival ICAO code.
     *
     * @param airport        The airport to check.
     * @param arrivalIcaoCode The desired arrival ICAO code.
     * @return True if the airport matches the desired code, otherwise false.
     */
    private boolean isMatchingDestination(Airport airport, String arrivalIcaoCode) {
        return airport.getIcaoCode().equals(arrivalIcaoCode);
    }
}
