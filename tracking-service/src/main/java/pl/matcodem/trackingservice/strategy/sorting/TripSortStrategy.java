package pl.matcodem.trackingservice.strategy.sorting;

import pl.matcodem.trackingservice.entity.Trip;

import java.util.List;

public interface TripSortStrategy {

    List<Trip> sort(List<Trip> trips);
}