package pl.matcodem.reservationservice.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class DateCalculator {
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public Period calculatePeriod(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate);
    }
}

