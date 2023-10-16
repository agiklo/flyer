package pl.matcodem.trackingservice.response;

import pl.matcodem.trackingservice.entity.Aircraft;
import pl.matcodem.trackingservice.enums.CabinClass;

import java.util.Set;

public record AircraftResponse(
        String callSign,
        Integer seatingCapacity,
        Integer luxurySeats,
        String model,
        boolean isNewAircraft,
        Set<CabinClass> classes
) {
    public static AircraftResponse fromAircraft(Aircraft aircraft) {
        return new AircraftResponse(
                aircraft.getCallSign(),
                aircraft.getSeatingCapacity(),
                aircraft.getLuxurySeats(),
                aircraft.getModel(),
                aircraft.isNewAircraft(),
                aircraft.getClasses()
        );
    }
}

