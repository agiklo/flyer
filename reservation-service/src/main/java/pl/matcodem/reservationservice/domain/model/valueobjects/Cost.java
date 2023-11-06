package pl.matcodem.reservationservice.domain.model.valueobjects;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record Cost(
        @NotNull(message = "Ticket price is required") BigDecimal ticketPrice,
        @NotNull(message = "Extra fees are required") BigDecimal extraFees
) {
}
