package pl.matcodem.reservationservice.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.matcodem.reservationservice.application.response.ReservationResponse;
import pl.matcodem.reservationservice.infrastructure.flight.FlightResponse;

@Component
public class FlightResponseMapper {

    public ReservationResponse.FlightInfo flightResponseToFlightInfo(FlightResponse flightResponse) {
        return ReservationResponse.FlightInfo.builder()
                .designatorCode(flightResponse.designatorCode())
                .departureAirport(flightResponse.departureAirport())
                .arrivalAirport(flightResponse.arrivalAirport())
                .airline(flightResponse.airline())
                .aircraft(flightResponse.aircraft())
                .cabin(flightResponse.cabin())
                .durationMinutes(flightResponse.durationMinutes())
                .departureDateTime(flightResponse.departureDateTime())
                .arrivalDateTime(flightResponse.arrivalDateTime())
                .build();
    }
}

