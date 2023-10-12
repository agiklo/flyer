package pl.matcodem.trackingservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.matcodem.trackingservice.entity.Aircraft;
import pl.matcodem.trackingservice.entity.Airline;
import pl.matcodem.trackingservice.entity.Airport;
import pl.matcodem.trackingservice.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.matcodem.trackingservice.enums.CabinClass.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlightRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirLineRepository airlineRepository;

    @Autowired
    AircraftRepository aircraftRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        Airport departureAirport = new Airport("EKCH", "Copenhagen Airport", "Europe", "Denmark", 55.620750, 12.650462);
        Airport arrivalAirport = new Airport("EPGD", "Gdansk Lech Walesa Airport", "Europe", "Poland", 54.377499, 18.466110);
        airportRepository.saveAndFlush(departureAirport);
        airportRepository.saveAndFlush(arrivalAirport);

        Airline airline = new Airline("117", "Scandinavian Airlines System (SAS)");
        airlineRepository.saveAndFlush(airline);

        Aircraft aircraft = new Aircraft("A319_3", 110, 0, "Airbus A319", true, Set.of(ECONOMY));
        aircraftRepository.saveAndFlush(aircraft);

        LocalDateTime departureDateTime = LocalDateTime.of(2023, 10, 4, 13, 35);
        Flight flight = new Flight("SAS1758", departureAirport, arrivalAirport, airline, aircraft, ECONOMY.name(), 55, departureDateTime, null);
        flightRepository.save(flight);
    }

    @Test
    void shouldFindFlightsByDepartureIcaoCodeAndDateTimeAfter() {
        var departureDateTime = LocalDateTime.of(2023, 10, 4, 6, 0);
        List<Flight> flights = flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter("EKCH", departureDateTime);
        assertNotNull(flights);
        assertThat(flights).isNotEmpty();
        assertEquals("Denmark", flights.get(0).getDepartureAirport().getCountry());
        assertEquals("Gdansk Lech Walesa Airport", flights.get(0).getArrivalAirport().getName());
    }

    @Test
    void shouldNotFindFlightsByDepartureIcaoCodeAndDateTimeAfter() {
        var departureDateTime = LocalDateTime.of(2023, 10, 6, 6, 0);
        List<Flight> flights = flightRepository.findFlightsByDepartureIcaoCodeAndDateTimeAfter("EKCH", departureDateTime);
        assertThat(flights).isNullOrEmpty();
    }
}