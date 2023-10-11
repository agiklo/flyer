package pl.matcodem.trackingservice.request;

import lombok.Builder;
import pl.matcodem.trackingservice.enums.CabinClass;
import pl.matcodem.trackingservice.enums.Currency;

import java.time.LocalDate;

@Builder
public record OnewayTripRequest(
        String departureAirportCode,
        String arrivalAirportCode,
        LocalDate departureDate,
        Integer numberOfAdults,
        Integer numberOfChildrens,
        Integer numberOfInfants,
        CabinClass cabinClass,
        Currency currency,
        Integer maxStopovers) {
}
