package pl.matcodem.trackingservice.service;

import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Airport;

/**
 * Service class for calculating the distance between two airports using their coordinates.
 */
@Service
public class AirportDistanceCalculatorService {


    /**
     * Calculates the distance between two airports using the Haversine formula.
     *
     * @param airport1 The first airport.
     * @param airport2 The second airport.
     * @return The distance between the two airports in kilometers.
     */
    public double calculateDistance(Airport airport1, Airport airport2) {
        double lat1 = Math.toRadians(airport1.getLatitude());
        double lon1 = Math.toRadians(airport1.getLongitude());
        double lat2 = Math.toRadians(airport2.getLatitude());
        double lon2 = Math.toRadians(airport2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = haversine(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dLon);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // The radius of the Earth in kilometers.
        final double EARTH_RADIUS = 6371.0;

        return EARTH_RADIUS * c;
    }

    /**
     * Calculates the haversine of an angle.
     *
     * @param angle The angle in radians.
     * @return The haversine of the angle.
     */
    private double haversine(double angle) {
        return Math.sin(angle / 2) * Math.sin(angle / 2);
    }
}
