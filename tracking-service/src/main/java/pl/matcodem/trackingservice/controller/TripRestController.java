package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.request.RoundtripRequest;
import pl.matcodem.trackingservice.response.RoundtripResponse;
import pl.matcodem.trackingservice.response.TripResponse;
import pl.matcodem.trackingservice.service.OnewayTripService;
import pl.matcodem.trackingservice.service.RoundtripService;

@RestController
@RequestMapping(name = "api/v1/trips")
@RequiredArgsConstructor
public class TripRestController {

    private final OnewayTripService onewayTripService;
    private final RoundtripService roundtripService;

    @GetMapping
    public Page<TripResponse> findOnewayTripByDestinationAndDate(@RequestBody OnewayTripRequest request) {
        return onewayTripService.findOnewayTrips(request);
    }

    @GetMapping
    public RoundtripResponse findRoundtripByDestinationAndDate(@RequestBody RoundtripRequest request) {
        return roundtripService.findRoundtrips(request);
    }
}
