package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pl.matcodem.trackingservice.request.OnewayTripRequest;
import pl.matcodem.trackingservice.response.TripResponse;
import pl.matcodem.trackingservice.service.OnewayTripService;
import pl.matcodem.trackingservice.strategy.sorting.SortStrategy;

@Controller
@RequiredArgsConstructor
public class TripGraphQlController {

    private final OnewayTripService onewayTripService;

    @QueryMapping(name = "findTripByDestinationAndDate")
    public Page<TripResponse> findTripByDestinationAndDate(@Argument OnewayTripRequest request, SortStrategy sortBy) {
        return onewayTripService.findOnewayTrips(request, sortBy);
    }
}
