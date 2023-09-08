package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.OnewayTripResponse;
import pl.matcodem.trackingservice.service.OnewayTripService;

@Controller
@RequiredArgsConstructor
public class TripController {

    private final OnewayTripService onewayTripService;

    @QueryMapping(name = "findTripByDestinationAndDate")
    public Page<OnewayTripResponse> findTripByDestinationAndDate(@Argument OnewayTripRequest request) {
        return onewayTripService.findOnewayTrips(request);
    }
}
