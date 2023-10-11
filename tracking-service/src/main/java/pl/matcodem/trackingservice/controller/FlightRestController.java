package pl.matcodem.trackingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.service.FlightService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightRestController {

    private final FlightService flightService;

    /**
     * Get a paginated list of all flights.
     *
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A page of Flight entities.
     */
    @GetMapping
    public Page<Flight> getAllFlights(
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.getAllFlights(page, size);
    }

    /**
     * Get a paginated list of flights by departure and arrival ICAO codes and date.
     *
     * @param departureAirport Departure ICAO code.
     * @param arrivalAirport Arrival ICAO code.
     * @param date Departure date.
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-departure-and-arrival-icao")
    public Page<Flight> getAllFlightsByDepartureAndArrivalIcao(
            @RequestParam("departureAirport") String departureAirport,
            @RequestParam("arrivalAirport") String arrivalAirport,
            @RequestParam("date") @Valid LocalDateTime date,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.findFlightsByDepartureAndArrivalAirports(departureAirport, arrivalAirport, date, page, size);
    }

    /**
     * Get a paginated list of flights by departure ICAO code and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param date Departure date.
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-departure-icao")
    public Page<Flight> getAllFlightsByDepartureIcao(
            @RequestParam("departureIcao") String departureIcao,
            @RequestParam("date") @Valid LocalDateTime date,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.getAllFlightsByDepartureIcao(departureIcao, date, page, size);
    }
}
