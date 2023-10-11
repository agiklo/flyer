package pl.matcodem.trackingservice.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.matcodem.trackingservice.enums.Currency;

import java.math.BigDecimal;

/**
 * Represents the price information associated with a trip.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {

    /**
     * The total amount, including all components (e.g., base fare, taxes, fees).
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalAmount;

    /**
     * The base fare amount before taxes and fees.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    /**
     * The amount per adult passenger.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amountPerAdult;

    /**
     * The amount per child passenger.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amountPerChild;

    /**
     * The amount per infant passenger.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amountPerInfant;

    /**
     * The total tax amount.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal taxAmount;

    /**
     * The total tax amount including taxes for all passengers.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalTaxAmount;

    /**
     * The currency code used for all monetary values in this price.
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Currency currencyCode;

    /**
     * The booking fee for the product or service.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal bookingFee;

    /**
     * The total booking fee including fees for all passengers.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalBookingFee;
}
