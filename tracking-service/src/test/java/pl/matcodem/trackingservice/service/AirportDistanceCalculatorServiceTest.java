package pl.matcodem.trackingservice.service;

import org.junit.jupiter.api.Test;
import pl.matcodem.trackingservice.entity.Airport;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AirportDistanceCalculatorServiceTest {

    @Test
    void testCalculateDistance() {
        AirportDistanceCalculatorService calculator = new AirportDistanceCalculatorService();

        // Define coordinates for two different airports: Warsaw Chopin Airport and Berlin Tegel Airport.
        Airport warsawAirport = createAirport(52.2297, 21.0122);
        Airport berlinAirport = createAirport(52.5200, 13.4050);

        // Calculate the distance between Warsaw and Berlin.
        double distance = calculator.calculateDistance(warsawAirport, berlinAirport);

        // The expected distance between Warsaw and Berlin (approximately).
        double expectedDistance = 517.17; // in kilometers

        // Define a tolerance for the test.
        double tolerance = 0.1; // A small tolerance for floating-point comparisons.

        // Assert that the calculated distance is close to the expected distance.
        assertEquals(expectedDistance, distance, tolerance);
    }

    @Test
    void testCalculateDistanceSameAirport() {
        AirportDistanceCalculatorService calculator = new AirportDistanceCalculatorService();

        // Define coordinates for the same airport: Warsaw Chopin Airport.
        Airport warsawAirport = createAirport(52.2297, 21.0122);

        // Calculate the distance between the same airport (should be zero).
        double distance = calculator.calculateDistance(warsawAirport, warsawAirport);

        // Assert that the calculated distance is zero.
        assertEquals(0.0, distance);
    }

    // Helper method to create an airport with the specified coordinates.
    private Airport createAirport(double latitude, double longitude) {
        Airport airport = new Airport();
        airport.setLatitude(latitude);
        airport.setLongitude(longitude);
        return airport;
    }
}
