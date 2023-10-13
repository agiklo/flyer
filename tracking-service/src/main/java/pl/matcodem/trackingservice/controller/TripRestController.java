package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.request.RoundTripRequest;
import pl.matcodem.trackingservice.response.RoundTripResponse;
import pl.matcodem.trackingservice.response.TripResponse;
import pl.matcodem.trackingservice.service.OnewayTripService;
import pl.matcodem.trackingservice.service.RoundtripService;
import pl.matcodem.trackingservice.strategy.sorting.SortStrategy;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripRestController {

    private final OnewayTripService onewayTripService;
    private final RoundtripService roundtripService;

    @GetMapping("/oneway-trip")
    public Page<TripResponse> findOnewayTripByDestinationAndDate(
            @RequestBody OnewayTripRequest request,
            @RequestParam(value = "sortBy", required = false) SortStrategy sortBy) {
        return onewayTripService.findOnewayTrips(request, sortBy);
    }

    @GetMapping("/round-trip")
    public RoundTripResponse findRoundTripByDestinationAndDate(
            @RequestBody RoundTripRequest request,
            @RequestParam(value = "sortBy", required = false) SortStrategy sortBy) {
        return roundtripService.findRoundTrips(request, sortBy);
    }
}
