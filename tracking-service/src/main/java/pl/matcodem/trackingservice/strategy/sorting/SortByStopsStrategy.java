package pl.matcodem.trackingservice.strategy.sorting;

import pl.matcodem.trackingservice.entity.Trip;

import java.util.Comparator;
import java.util.List;

public class SortByStopsStrategy implements TripSortStrategy {

    @Override
    public List<Trip> sort(List<Trip> trips) {
        trips.sort(Comparator.comparing(Trip::getStopoversCount));
        return trips;
    }
}
