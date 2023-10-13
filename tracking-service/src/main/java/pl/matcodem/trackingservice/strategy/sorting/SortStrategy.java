package pl.matcodem.trackingservice.strategy.sorting;

public enum SortStrategy {
    PRICE,
    DURATION,
    STOPS;

    public static TripSortStrategy getSortStrategy(SortStrategy sortBy) {
        return switch (sortBy) {
            case PRICE -> new SortByPriceStrategy();
            case DURATION -> new SortByDurationStrategy();
            case STOPS -> new SortByStopsStrategy();
        };
    }
}

