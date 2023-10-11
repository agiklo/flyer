package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.mapper.TripMapper;
import pl.matcodem.trackingservice.request.RoundtripRequest;
import pl.matcodem.trackingservice.response.RoundtripResponse;
import pl.matcodem.trackingservice.response.TripResponse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundtripService {

    private final TripMapper tripMapper;
    private final TripFinderService tripFinder;

    /**
     * Find roundtrip options based on the provided request.
     *
     * @param request The roundtrip request specifying departure, arrival, dates, and maximum stopovers.
     * @return A roundtrip response containing first and second leg trip options.
     */
    public RoundtripResponse findRoundtrips(RoundtripRequest request) {
        String departureIcaoCode = request.departureAirportCode();
        String arrivalIcaoCode = request.arrivalAirportCode();
        LocalDate departureDate = request.departureDate();
        LocalDate returnDate = request.returnDate();
        int maxStopovers = request.maxStopovers();

        List<TripResponse> firstWayTrips = findTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);
        List<TripResponse> secondWayTrips = findTrips(arrivalIcaoCode, departureIcaoCode, returnDate, maxStopovers);

        return RoundtripResponse.builder()
                .firstTrips(firstWayTrips)
                .secondTrips(secondWayTrips)
                .build();
    }

    private List<TripResponse> findTrips(String departureIcaoCode, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers) {
        List<Trip> possibleTrips = tripFinder.findPossibleTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);
        return possibleTrips.stream()
                .sorted(Comparator.comparingInt(Trip::getDurationMinutes))
                .map(tripMapper::mapTripToTripResponse)
                .toList();
    }
}
