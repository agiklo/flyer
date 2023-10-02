package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;
import pl.matcodem.trackingservice.service.OnewayTripService;

@RestController
@RequestMapping(name = "api/v1/trips")
@RequiredArgsConstructor
public class TripRestController {

    private final OnewayTripService onewayTripService;

    @GetMapping
    public Page<OnewayTripResponse> findTripByDestinationAndDate(@RequestBody OnewayTripRequest request) {
        return onewayTripService.findOnewayTrips(request);
    }
}
