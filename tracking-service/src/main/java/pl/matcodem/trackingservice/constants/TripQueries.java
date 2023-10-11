package pl.matcodem.trackingservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TripQueries {

    public static final String GET_TRIPS_BY_DEPARTURE_ICAO_CODE = "Trip.getTripsByDepartureIcaoCode";
    public static final String GET_TRIPS_BY_ARRIVAL_ICAO_CODE = "Trip.getTripsByArrivalIcaoCode";
    public static final String GET_TRIPS_BY_ICAO_CODES_AND_DEPARTURE_DATE = "Trip.getTripsByIcaoCodesAndDepatureDate";

    public static final String SQL_GET_TRIPS_BY_DEPARTURE_ICAO_CODE = "SELECT t FROM Trip t WHERE t.departureAirport.icaoCode =:code";
    public static final String SQL_GET_TRIPS_BY_ARRIVAL_ICAO_CODE = "SELECT t FROM Trip t WHERE t.arrivalAirport.icaoCode =:code";
    public static final String SQL_GET_TRIPS_BY_ICAO_CODES_AND_DEPARTURE_DATE = "SELECT t FROM Trip t WHERE " +
            "t.arrivalAirport.icaoCode =:arrivalCode AND " +
            "t.departureAirport.icaoCode =:departureCode AND " +
            "t.departureDateTime =: date";
}

