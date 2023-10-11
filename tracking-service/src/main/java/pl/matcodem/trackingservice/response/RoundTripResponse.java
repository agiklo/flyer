package pl.matcodem.trackingservice.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoundTripResponse(List<TripResponse> firstTrips,
                                List<TripResponse> secondTrips) {
}
