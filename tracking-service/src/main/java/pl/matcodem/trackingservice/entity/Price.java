package pl.matcodem.trackingservice.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.matcodem.trackingservice.enums.Currency;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {

    private BigDecimal totalAmount;
    private BigDecimal amount;
    private BigDecimal amountPerAdult;
    private BigDecimal amountPerChild;
    private BigDecimal amountPerInfant;
    private BigDecimal taxAmount;
    private BigDecimal totalTaxAmount;

    @Enumerated(value = EnumType.STRING)
    private Currency currencyCode;

    private BigDecimal bookingFee;
    private BigDecimal totalBookingFee;
}
