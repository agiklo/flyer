package pl.matcodem.trackingservice.response;

import lombok.Getter;
import lombok.Setter;
import pl.matcodem.trackingservice.entity.Flight;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightResponse {
    private String designatorCode;
    private String departureAirport;
    private String arrivalAirport;
    private String airline;
    private String aircraft;
    private String cabin;
    private Integer durationMinutes;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    public FlightResponse(Flight flight) {
        this.designatorCode = flight.getDesignatorCode();
        this.departureAirport = flight.getDepartureAirport().getIcaoCode();
        this.arrivalAirport = flight.getArrivalAirport().getIcaoCode();
        this.airline = flight.getAirline().getName();
        this.aircraft = flight.getAircraft().getModel();
        this.cabin = flight.getCabin();
        this.durationMinutes = flight.getDurationMinutes();
        this.departureDateTime = flight.getDepartureDateTime();
        this.arrivalDateTime = flight.getArrivalDateTime();
    }
}

