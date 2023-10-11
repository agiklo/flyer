package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.mapper.TripMapper;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.TripResponse;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OnewayTripService {

    private final TripMapper tripMapper;
    private final TripFinderService tripFinder;

    /**
     * Find one-way trips based on the provided request with a maximum number of stopovers.
     *
     * @param request The request specifying departure, arrival, date, and maximum stopovers.
     * @return A page of one-way trip responses.
     */
    public Page<TripResponse> findOnewayTrips(OnewayTripRequest request) {
        String departureIcaoCode = request.departureAirportCode();
        String arrivalIcaoCode = request.arrivalAirportCode();
        LocalDate departureDate = request.departureDate();
        int maxStopovers = request.maxStopovers();

        List<TripResponse> possibleTrips = findTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);
        return new PageImpl<>(possibleTrips);
    }

    private List<TripResponse> findTrips(String departureIcaoCode, String arrivalIcaoCode, LocalDate departureDate, int maxStopovers) {
        List<Trip> possibleTrips = tripFinder.findPossibleTrips(departureIcaoCode, arrivalIcaoCode, departureDate, maxStopovers);
        return possibleTrips.stream()
                .sorted(Comparator.comparingInt(Trip::getDurationMinutes))
                .map(tripMapper::mapTripToTripResponse)
                .toList();
    }
}
