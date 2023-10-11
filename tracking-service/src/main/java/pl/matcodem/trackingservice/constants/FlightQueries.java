package pl.matcodem.trackingservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlightQueries {

    public static final String FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS = "Flight.findFlightsByDepartureAndArrivalAirports";
    public static final String FIND_FLIGHTS_BY_DEPARTURE_ICAO_AND_DATE_TIME_AFTER = "Flight.findFlightsByDepartureIcaoCodeAndDateTimeAfter";
    public static final String FIND_FLIGHTS_BY_ARRIVAL_AIRPORT = "Flight.findFlightsByArrivalAirport";
    public static final String FIND_FLIGHTS_BY_DEPARTURE_AIRPORT = "Flight.findFlightsByDepartureAirport";
    public static final String FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS_AND_DATE = "Flight.findFlightsByDepartureAndArrivalAirportsAndDate";

    // SQL queries as constants
    public static final String SQL_FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS = """
        SELECT f FROM Flight f WHERE
        f.arrivalAirport.icaoCode = :arrivalIcao
        AND
        f.departureAirport.icaoCode = :departureIcao
    """;

    public static final String SQL_FIND_FLIGHTS_BY_DEPARTURE_ICAO_AND_DATE_TIME_AFTER = """
        SELECT f FROM Flight f WHERE
        f.departureAirport.icaoCode = :departureIcao
        AND
        f.departureDateTime >= :date
    """;

    public static final String SQL_FIND_FLIGHTS_BY_ARRIVAL_AIRPORT = "SELECT f FROM Flight f WHERE f.arrivalAirport.icaoCode = :arrivalIcao";
    public static final String SQL_FIND_FLIGHTS_BY_DEPARTURE_AIRPORT = "SELECT f FROM Flight f WHERE f.departureAirport.icaoCode = :departureIcao";
    public static final String SQL_FIND_FLIGHTS_BY_DEPARTURE_AND_ARRIVAL_AIRPORTS_AND_DATE = """
        SELECT f FROM Flight f WHERE
        f.arrivalAirport.icaoCode = :arrivalIcao
        AND
        f.departureAirport.icaoCode = :departureIcao
        AND
        f.departureDateTime >= :date
    """;
}

