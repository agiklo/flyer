package pl.matcodem.trackingservice.request;

import lombok.Builder;
import pl.matcodem.trackingservice.enums.CabinClass;
import pl.matcodem.trackingservice.enums.Currency;

import java.time.LocalDate;

@Builder
public record RoundtripRequest(
        String departureAirportCode,
        String arrivalAirportCode,
        LocalDate departureDate,
        LocalDate returnDate,
        Integer numberOfAdults,
        Integer numberOfChildren,
        Integer numberOfInfants,
        CabinClass cabinClass,
        Currency currency,
        Integer maxStopovers) {
}
