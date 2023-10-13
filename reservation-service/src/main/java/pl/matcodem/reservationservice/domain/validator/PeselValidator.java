package pl.matcodem.reservationservice.domain.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Utility class for PESEL (Personal Identification Number) validation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeselValidator {

    private static final Pattern PESEL_PATTERN = Pattern.compile("\\d{11}");

    /**
     * Validates a PESEL number.
     *
     * @param pesel The PESEL number to validate.
     * @return True if the PESEL is valid, false otherwise.
     */
    public static boolean isValidPesel(String pesel) {
        if (pesel == null) {
            return false;
        }
        if (!PESEL_PATTERN.matcher(pesel).matches()) {
            return false;
        }
        return isValidPeselChecksum(pesel);
    }

    private static boolean isValidPeselChecksum(String pesel) {
        if (pesel.length() != 11) {
            return false;
        }
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Integer.parseInt(pesel.substring(i, i + 1)) * weights[i];
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }
        int lastDigit = Integer.parseInt(pesel.substring(10, 11));
        return lastDigit == checksum;
    }
}
