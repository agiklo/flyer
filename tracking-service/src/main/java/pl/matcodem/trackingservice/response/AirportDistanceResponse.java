package pl.matcodem.trackingservice.response;

public record AirportDistanceResponse(String originIcaoCode, String destinationIcaoCode, double distanceInKilometers) {
}
