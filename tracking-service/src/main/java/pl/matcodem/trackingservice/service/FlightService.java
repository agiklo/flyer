package pl.matcodem.trackingservice.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.exceptions.FlightNotFoundException;
import pl.matcodem.trackingservice.repository.FlightRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static pl.matcodem.trackingservice.validator.IcaoCodeValidator.isValidIcaoCode;

/**
 * Service class for handling flight-related operations.
 */
@Service
@RequiredArgsConstructor
public class FlightService {

    public static final String ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY = "ICAO code must not be null or empty.";
    public static final String ERROR_OCCURRED_WHILE_SEARCHING_FOR_FLIGHTS = "An error occurred while searching for flights.";
    public static final String DEPARTURE_DATE_TIME = "departureDateTime";


    private final FlightRepository flightRepository;

    /**
     * Get a paginated list of all flights.
     *
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A page of Flight entities.
     */
    public Page<Flight> getAllFlights(@Min(0) int page, @Min(1) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(DEPARTURE_DATE_TIME));
        return flightRepository.findAll(pageable);
    }

    /**
     * Get a paginated list of flights by departure ICAO code and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param date          Departure date.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     * @throws IllegalArgumentException if the departure ICAO code is null or empty.
     */
    public Page<Flight> getAllFlightsByDepartureIcao(String departureIcao, @NotNull LocalDateTime date, @Min(0) int page, @Min(1) int size) {
        if (!isValidIcaoCode(departureIcao)) {
            throw new IllegalArgumentException(ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(DEPARTURE_DATE_TIME));
        return flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter(departureIcao, date, pageable);
    }

    /**
     * Get a list of flights by departure ICAO code and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param date          Departure date.
     * @return A list of Flight entities matching the criteria.
     * @throws IllegalArgumentException if the departure ICAO code is null or empty.
     */
    public List<Flight> getAllFlightsByDepartureIcao(String departureIcao, @NotNull LocalDate date) {
        if (!isValidIcaoCode(departureIcao)) {
            throw new IllegalArgumentException(ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY);
        }

        return flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter(departureIcao, date.atStartOfDay());
    }

    /**
     * Get a paginated list of flights by departure and arrival ICAO codes and date.
     *
     * @param departureIcao Departure ICAO code.
     * @param arrivalIcao   Arrival ICAO code.
     * @param departureDate Departure date.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     * @throws IllegalArgumentException if the departure or arrival ICAO codes are null or empty.
     * @throws FlightNotFoundException  if an error occurs while searching for flights.
     */
    public Page<Flight> findFlightsByDepartureAndArrivalAirportsAndDate(
            String departureIcao, String arrivalIcao, @NotNull LocalDateTime departureDate, @Min(0) int page, @Min(1) int size) {
        try {
            if (!isValidIcaoCode(departureIcao) || !isValidIcaoCode(arrivalIcao)) {
                throw new IllegalArgumentException(ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY);
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by(DEPARTURE_DATE_TIME));
            return flightRepository.findFlightsByDepartureAndArrivalAirportsAndDate(
                    departureIcao, arrivalIcao, departureDate, pageable);
        } catch (Exception e) {
            throw new FlightNotFoundException(ERROR_OCCURRED_WHILE_SEARCHING_FOR_FLIGHTS, e);
        }
    }

    /**
     * Get a paginated list of flights by departure and arrival ICAO codes.
     *
     * @param departureIcao Departure ICAO code.
     * @param arrivalIcao   Arrival ICAO code.
     * @param page          Page number (default is 0).
     * @param size          Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     * @throws IllegalArgumentException if the departure or arrival ICAO codes are null or empty.
     * @throws FlightNotFoundException  if an error occurs while searching for
     *                                  <p>
     *                                  flights.
     */
    public Page<Flight> findFlightsByDepartureAndArrivalAirports(
            String departureIcao, String arrivalIcao, @Min(0) int page, @Min(1) int size) {
        try {
            if (!isValidIcaoCode(departureIcao) || !isValidIcaoCode(arrivalIcao)) {
                throw new IllegalArgumentException(ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY);
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by(DEPARTURE_DATE_TIME));
            return flightRepository.findFlightsByDepartureAndArrivalAirports(
                    departureIcao, arrivalIcao, pageable);
        } catch (Exception e) {
            throw new FlightNotFoundException(ERROR_OCCURRED_WHILE_SEARCHING_FOR_FLIGHTS, e);
        }
    }

    /**
     * Get a paginated list of flights by arrival ICAO code.
     *
     * @param arrivalIcao Arrival ICAO code.
     * @param page        Page number (default is 0).
     * @param size        Number of items per page (default is 10).
     * @return A page of Flight entities matching the criteria.
     * @throws IllegalArgumentException if the arrival ICAO code is null or empty.
     * @throws FlightNotFoundException  if an error occurs while searching for flights.
     */
    public Page<Flight> findFlightsByArrivalAirport(String arrivalIcao, @Min(0) int page, @Min(1) int size) {
        try {
            if (!isValidIcaoCode(arrivalIcao)) {
                throw new IllegalArgumentException(ICAO_CODE_MUST_NOT_BE_NULL_OR_EMPTY);
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by(DEPARTURE_DATE_TIME));
            return flightRepository.findFlightsByArrivalAirport(arrivalIcao, pageable);
        } catch (Exception e) {
            throw new FlightNotFoundException(ERROR_OCCURRED_WHILE_SEARCHING_FOR_FLIGHTS, e);
        }
    }

    /**
     * Retrieve a flight by its designator code.
     *
     * @param designatorCode The designator code of the flight to retrieve (not null or empty).
     * @return The Flight entity matching the designator code.
     * @throws IllegalArgumentException     if the designator code is null or empty.
     * @throws FlightNotFoundException       if no flight with the given designator code is found.
     */
    public Flight findFlightByDesignatorCode(@NotBlank String designatorCode) {
        if (designatorCode == null) {
            throw new IllegalArgumentException("Flight designator code must not be null.");
        }
        return flightRepository.findById(designatorCode)
                .orElseThrow(() -> new FlightNotFoundException("Flight with designator code " + designatorCode + " not found"));
    }
}