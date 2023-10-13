package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Trip;

@Service
@RequiredArgsConstructor
public class CO2EmissionsService {

    private final AirportDistanceCalculatorService airportDistanceCalculatorService;

    // CO2 emission constant per passenger kilometer
    private static final double CO2_EMISSION_PER_PASSENGER_KM = 0.115;

    /**
     * Calculates the total CO2 emissions for a trip, taking into account the distance traveled and the number of passengers.
     *
     * @param trip The trip for which emissions are calculated.
     * @param numberOfPassengers The number of passengers on the trip.
     * @return The total CO2 emissions in kilograms (kg) for the trip.
     * @throws IllegalArgumentException if the provided distance or number of passengers is non-positive.
     */
    public double calculateCO2Emission(Trip trip, int numberOfPassengers) {
        double distanceInKilometers = airportDistanceCalculatorService.calculateDistance(trip.getArrivalAirport(), trip.getDepartureAirport());
        validateInput(distanceInKilometers, numberOfPassengers);

        double totalCO2Emission = calculateTotalCO2Emission(distanceInKilometers, numberOfPassengers);
        return convertToKilograms(totalCO2Emission);
    }

    private void validateInput(double distanceInKilometers, int numberOfPassengers) {
        if (distanceInKilometers <= 0 || numberOfPassengers <= 0) {
            throw new IllegalArgumentException("Distance and number of passengers must be positive values.");
        }
    }

    private double calculateTotalCO2Emission(double distanceInKilometers, int numberOfPassengers) {
        return CO2_EMISSION_PER_PASSENGER_KM * distanceInKilometers * numberOfPassengers;
    }

    private double convertToKilograms(double totalCO2Emission) {
        return totalCO2Emission / 1000;
    }
}