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
import pl.matcodem.trackingservice.entity.Airport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AirportRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    AirportRepository airportRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        var airports = List.of(
                new Airport("EKCH", "Copenhagen Airport", "Europe", "Denmark", 55.620750, 12.650462),
                new Airport("EPGD", "Gdansk Lech Walesa Airport", "Europe", "Poland", 54.377499, 18.466110)
        );
        airportRepository.saveAll(airports);
    }

    @Test
    void shouldReturnAirportByIcaoCode() {
        Airport airport = airportRepository.getAirportByIcaoCode("EKCH").get();
        assertThat(airport).isNotNull();
        assertThat(airport.getCountry()).isEqualTo("Denmark");
        assertThat(airport.getName()).isEqualTo("Copenhagen Airport");
    }
}