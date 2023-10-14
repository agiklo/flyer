package pl.matcodem.trackingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.trackingservice.response.FlightResponse;
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
    public Page<FlightResponse> getAllFlights(
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.getAllFlights(page, size);
    }

    /**
     * Get a paginated list of flights by departure and arrival ICAO codes and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param arrivalIcao   Arrival ICAO code.
     * @param date          Departure date.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-departure-and-arrival-icao-and-date")
    public Page<FlightResponse> findFlightsByDepartureAndArrivalAirportsAndDate(
            @RequestParam("departureIcao") String departureIcao,
            @RequestParam("arrivalIcao") String arrivalIcao,
            @RequestParam("date") @Valid LocalDateTime date,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.findFlightsByDepartureAndArrivalAirportsAndDate(departureIcao, arrivalIcao, date, page, size);
    }

    /**
     * Get a paginated list of flights by departure ICAO code and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param date          Departure date.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-departure-icao")
    public Page<FlightResponse> getAllFlightsByDepartureIcao(
            @RequestParam("departureIcao") String departureIcao,
            @RequestParam("date") @Valid LocalDateTime date,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.getAllFlightsByDepartureIcao(departureIcao, date, page, size);
    }

    /**
     * Search flights by departure and arrival ICAO codes.
     *
     * @param departureIcao The departure ICAO code.
     * @param arrivalIcao   The arrival ICAO code.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-departure-and-arrival-icao")
    public Page<FlightResponse> findFlightsByDepartureAndArrivalAirports(
            @RequestParam("departureIcao") String departureIcao,
            @RequestParam("arrivalIcao") String arrivalIcao,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.findFlightsByDepartureAndArrivalAirports(departureIcao, arrivalIcao, page, size);
    }

    /**
     * Get a paginated list of flights by arrival ICAO code.
     *
     * @param arrivalIcao Arrival ICAO code.
     * @param page        Page number (default is 0).
     * @param size        Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     */
    @GetMapping("/by-arrival-icao")
    public Page<FlightResponse> findFlightsByArrivalAirport(
            @RequestParam("arrivalIcao") String arrivalIcao,
            @RequestParam(value = "page", defaultValue = "0") @Valid int page,
            @RequestParam(value = "size", defaultValue = "10") @Valid int size) {
        return flightService.findFlightsByArrivalAirport(arrivalIcao, page, size);
    }

    /**
     * Retrieves a flight by its designator code.
     *
     * @param designatorCode The designator code of the flight to retrieve.
     * @return The Flight entity matching the designator code.
     */
    @GetMapping("/by-designator-code/{designatorCode}")
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse findFlightByDesignatorCode(@PathVariable("designatorCode") @Valid String designatorCode) {
        return flightService.findFlightByDesignatorCode(designatorCode);
    }
}
