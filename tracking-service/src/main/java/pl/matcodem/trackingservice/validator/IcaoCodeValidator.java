package pl.matcodem.trackingservice.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IcaoCodeValidator {

    private static final String ICAO_CODE_REGEX = "^[A-Z0-9]{4}$";

    public static boolean isValidIcaoCode(String icaoCode) {
        if (icaoCode == null || icaoCode.isEmpty()) {
            return false;
        }
        return icaoCode.matches(ICAO_CODE_REGEX);
    }
}

