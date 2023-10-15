package pl.matcodem.trackingservice.service.external.api.airport;

public record AirportInfo(
        String airport_name,
        String city,
        String country,
        String iata,
        String icao,
        double latitude,
        double longitude,
        int elevation,
        int utc_offset,
        String _class,
        String timezone
) { }

