package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.repository.TripRepository;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;
import pl.matcodem.trackingservice.response.Leg;

import java.util.List;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class OnewayTripService {

    private final TripRepository tripRepository;

    public Page<OnewayTripResponse> findOnewayTrips(OnewayTripRequest request) {
        var departureIcaoCode = request.departureAirportCode();
        var arrivalIcaoCode = request.arrivalAirportCode();
        var departureDate = request.departureDate().atStartOfDay();
        List<Trip> foundTrips = tripRepository.getTripsByIcaoCodesAndDepatureDate(departureIcaoCode, arrivalIcaoCode, departureDate);
        var mappedTrips = foundTrips.stream().map(this::mapTripToOnewayTripResponse).toList();
        return new PageImpl<>(mappedTrips);
    }

    private OnewayTripResponse mapTripToOnewayTripResponse(Trip trip) {
        var legs = trip.getFlights().stream()
                .map(this::mapFlightToLegResponse)
                .collect(toSet());
        return OnewayTripResponse.builder()
                .tripId(trip.getTripId())
                .departureTime(trip.getDepartureTime())
                .departureAirportCode(trip.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(trip.getArrivalAirport().getIcaoCode())
                .airlineCodes(trip.getAirlineCodes())
                .stopoverAirportCodes(trip.getStopoverAiportCodes())
                .allianceCodes(trip.getAllianceCodes())
                .stopoversCount(trip.getstopoversCount())
                .departureDateTime(trip.getDepartureDateTime())
                .arrivalDateTime(trip.getArrivalDateTime())
                .stopoverDurationMinutes(trip.getStopoverDurationMinutes())
                .durationMinutes(trip.getDurationMinutes())
                .overnight(trip.isOvernight())
                .stopoverDuration(trip.getStopoverDuration())
                .durationDays(trip.getDurationInDays())
                .longStopover(trip.containsLongStopover())
                .legs(legs)
                .shortStopover(trip.containsShortStopover())
                .earlyDeparture(trip.isEarlyDeparture())
                .lateArrival(trip.isLateArrival())
                .newAircraft(trip.containsNewAircraft())
                .oldAircraft(trip.containsOldAircraft())
                .highlyRatedCarrier(trip.getHighlyRatedCarrier())
                .score(trip.getScore())
                .build();
    }

    private Leg mapFlightToLegResponse(Flight flight) {
        return Leg.builder()
                .durationMinutes(flight.getDurationMinutes())
                .stopoverDurationMinutes(flight.getStopover().getStopoverDurationMinutes())
                .departureAirportCode(flight.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(flight.getArrivalAirport().getIcaoCode())
                .airlineCode(flight.getAirline().getAirlineCode())
                .cabin(flight.getCabin())
                .designatorCode(flight.getDesignatorCode())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalDateTime(flight.getArrivalDateTime())
                .build();
    }
}
