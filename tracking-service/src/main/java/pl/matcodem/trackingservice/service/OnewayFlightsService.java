package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.entity.Segment;
import pl.matcodem.trackingservice.repository.FlightRepository;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;
import pl.matcodem.trackingservice.response.SegmentResponse;

import java.util.List;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class OnewayFlightsService {

    private final FlightRepository flightRepository;

    public Page<OnewayTripResponse> findOnewayFilghts(OnewayTripRequest request) {
        var departureIcaoCode = request.departureAirportCode();
        var arrivalIcaoCode = request.arrivalAirportCode();
        var departureDate = request.departureDate().atStartOfDay();
        List<Flight> foundFlights = flightRepository.getFlightsByIcaoCodesAndDepatureDate(departureIcaoCode, arrivalIcaoCode, departureDate);
        var mappedFligts = foundFlights.stream().map(this::mapFlightToOnewayTripResponse).toList();
        return new PageImpl<>(mappedFligts);
    }

    private OnewayTripResponse mapFlightToOnewayTripResponse(Flight flight) {
        var mappedSegments = flight.getSegments().stream()
                .map(this::mapSegmentToResponse)
                .collect(toSet());
        return OnewayTripResponse.builder()
                .flightId(flight.getFlightId())
                .departureTime(flight.getDepartureTime())
                .departureAirportCode(flight.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(flight.getArrivalAirport().getIcaoCode())
                .airlineCodes(flight.getAirlineCodes())
                .stopoverAirportCodes(flight.getStopoverAiportCodes())
                .allianceCodes(flight.getAllianceCodes())
                .stopoversCount(flight.getstopoversCount())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalDateTime(flight.getArrivalDateTime())
                .stopoverDurationMinutes(flight.getStopoverDurationMinutes())
                .durationMinutes(flight.getDurationMinutes())
                .overnight(flight.isOvernight())
                .stopoverDuration(flight.getStopoverDuration())
                .durationDays(flight.getDurationInDays())
                .longStopover(flight.containsLongStopover())
                .segments(mappedSegments)
                .shortStopover(flight.containsShortStopover())
                .earlyDeparture(flight.isEarlyDeparture())
                .lateArrival(flight.isLateArrival())
                .newAircraft(flight.containsNewAircraft())
                .oldAircraft(flight.containsOldAircraft())
                .highlyRatedCarrier(flight.getHighlyRatedCarrier())
                .score(flight.getScore())
                .build();
    }

    private SegmentResponse mapSegmentToResponse(Segment segment) {
        return SegmentResponse.builder()
                .durationMinutes(segment.getDurationMinutes())
                .stopoverDurationMinutes(segment.getStopover().getStopoverDurationMinutes())
                .departureAirportCode(segment.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(segment.getArrivalAirport().getIcaoCode())
                .airlineCode(segment.getAirline().getAirlineCode())
                .cabin(segment.getCabin())
                .designatorCode(segment.getDesignatorCode())
                .departureDateTime(segment.getDepartureDateTime())
                .arrivalDateTime(segment.getArrivalDateTime())
                .build();
    }
}
