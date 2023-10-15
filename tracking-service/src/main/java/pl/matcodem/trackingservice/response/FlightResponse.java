package pl.matcodem.trackingservice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.enums.CabinClass;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FlightResponse {
    private String designatorCode;
    private String departureAirport;
    private String arrivalAirport;
    private String airline;
    private String aircraft;
    private Set<CabinClass> cabins;
    private Integer durationMinutes;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    public FlightResponse(Flight flight) {
        this.designatorCode = flight.getDesignatorCode();
        this.departureAirport = flight.getDepartureAirport().getIcaoCode();
        this.arrivalAirport = flight.getArrivalAirport().getIcaoCode();
        this.airline = flight.getAirline().getName();
        this.aircraft = flight.getAircraft().getModel();
        this.cabins = flight.getCabins();
        this.durationMinutes = flight.getDurationMinutes();
        this.departureDateTime = flight.getDepartureDateTime();
        this.arrivalDateTime = flight.getArrivalDateTime();
    }
}

