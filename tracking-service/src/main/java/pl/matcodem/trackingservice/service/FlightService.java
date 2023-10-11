package pl.matcodem.trackingservice.service;

import jakarta.validation.constraints.Min;
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

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Page<Flight> getAllFlights(@Min(0) int page, @Min(1) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("departureDateTime"));
        return flightRepository.findAll(pageable);
    }

    public Page<Flight> getAllFlightsByDepartureIcao(String departureIcao, @NotNull LocalDateTime date, @Min(0) int page, @Min(1) int size) {
        if (!isValidIcaoCode(departureIcao)) {
            throw new IllegalArgumentException("Departure ICAO code must not be null or empty.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("departureDateTime"));
        return flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter(departureIcao, date, pageable);
    }

    public List<Flight> getAllFlightsByDepartureIcao(String departureIcao, @NotNull LocalDate date) {
        if (!isValidIcaoCode(departureIcao)) {
            throw new IllegalArgumentException("Departure ICAO code must not be null or empty.");
        }

        return flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter(departureIcao, date.atStartOfDay());
    }

    public Page<Flight> findFlightsByDepartureAndArrivalAirports(
            String departureIcao, String arrivalIcao, @NotNull LocalDateTime departureDate, @Min(0) int page, @Min(1) int size) {
        try {
            if (!isValidIcaoCode(departureIcao) || !isValidIcaoCode(arrivalIcao)) {
                throw new IllegalArgumentException("ICAO code must not be null or empty.");
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by("departureDateTime"));
            return flightRepository.findFlightsByDepartureAndArrivalAirportsAndDate(
                    departureIcao, arrivalIcao, departureDate, pageable);
        } catch (Exception e) {
            throw new FlightNotFoundException("Wystąpił błąd podczas wyszukiwania lotów.", e);
        }
    }
}
