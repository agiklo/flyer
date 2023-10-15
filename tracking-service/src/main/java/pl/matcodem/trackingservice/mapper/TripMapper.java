package pl.matcodem.trackingservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.entity.Trip;
import pl.matcodem.trackingservice.response.Leg;
import pl.matcodem.trackingservice.response.TripResponse;
import pl.matcodem.trackingservice.service.CO2EmissionsService;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class TripMapper {

    private final CO2EmissionsService co2EmissionsService;

    public TripResponse mapTripToTripResponse(Trip trip) {
        var legs = trip.getFlights().stream()
                .map(this::mapFlightToLegResponse)
                .collect(toSet());

        double co2EmissionPerPerson = co2EmissionsService.calculateCO2Emission(trip, 1);

        return TripResponse.builder()
                .tripId(trip.getTripId())
                .departureTime(trip.getDepartureTime())
                .departureAirportCode(trip.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(trip.getArrivalAirport().getIcaoCode())
                .airlineCodes(trip.getAirlineCodes())
                .stopoverAirportCodes(trip.getStopoverAirportCodes())
                .allianceCodes(trip.getAllianceCodes())
                .stopoversCount(trip.getStopoversCount())
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
                .co2EmissionPerPerson(co2EmissionPerPerson)
                .build();
    }

    private Leg mapFlightToLegResponse(Flight flight) {
        return Leg.builder()
                .durationMinutes(flight.getDurationMinutes())
                .stopoverDurationMinutes(flight.getStopover().getStopoverDurationMinutes())
                .departureAirportCode(flight.getDepartureAirport().getIcaoCode())
                .arrivalAirportCode(flight.getArrivalAirport().getIcaoCode())
                .airlineCode(flight.getAirline().getAirlineCode())
                .cabins(flight.getCabins())
                .designatorCode(flight.getDesignatorCode())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalDateTime(flight.getArrivalDateTime())
                .build();
    }
}
