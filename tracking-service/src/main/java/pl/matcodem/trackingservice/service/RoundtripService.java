package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.mapper.TripMapper;
import pl.matcodem.trackingservice.request.RoundTripRequest;
import pl.matcodem.trackingservice.response.RoundTripResponse;
import pl.matcodem.trackingservice.response.TripResponse;
import pl.matcodem.trackingservice.strategy.sorting.SortStrategy;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
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
    public RoundTripResponse findRoundTrips(RoundTripRequest request, SortStrategy sortBy) {
        String departureIcaoCode = request.departureAirportCode();
        String arrivalIcaoCode = request.arrivalAirportCode();
        LocalDate departureDate = request.departureDate();
        LocalDate returnDate = request.returnDate();
        int maxStopovers = request.maxStopovers();

        List<TripResponse> firstWayTrips = findTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers, sortBy);
        List<TripResponse> secondWayTrips = findTrips(arrivalIcaoCode, departureIcaoCode, returnDate, maxStopovers, sortBy);

        return RoundTripResponse.builder()
                .firstTrips(firstWayTrips)
                .secondTrips(secondWayTrips)
                .build();
    }

    private List<TripResponse> findTrips(String departureIcaoCode, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers, SortStrategy sortBy) {
        List<Trip> possibleTrips = tripFinder.findPossibleTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);
        var sortStrategy = SortStrategy.getSortStrategy(sortBy);
        List<Trip> sortedTrips = sortStrategy.sort(possibleTrips);
        return sortedTrips.stream()
                .map(tripMapper::mapTripToTripResponse)
                .toList();
    }
}
