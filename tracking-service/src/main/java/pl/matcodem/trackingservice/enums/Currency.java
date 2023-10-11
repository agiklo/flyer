package pl.matcodem.trackingservice.enums;

import java.math.BigDecimal;

// For simplicity, I use hardcoded rates for currencies
public enum Currency {
    USD("United States Dollar", "$", 2),
    EUR("Euro", "€", 2),
    GBP("British Pound Sterling", "£", 2),
    JPY("Japanese Yen", "¥", 0),
    CAD("Canadian Dollar", "C$", 2),
    PLN("Polish Złoty", "zł", 2);

    private final String displayName;
    private final String symbol;
    private final int decimalPlaces;

    Currency(String displayName, String symbol, int decimalPlaces) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Convert an amount from this currency to the target currency.
     *
     * @param amount         The amount to convert.
     * @param targetCurrency The target currency.
     * @return The converted amount in the target currency.
     */
    public BigDecimal convertTo(BigDecimal amount, Currency targetCurrency) {
        if (this == targetCurrency) {
            return amount;
        }

        BigDecimal exchangeRate = getExchangeRate(targetCurrency);
        return amount.multiply(exchangeRate);
    }

    /**
     * Get the exchange rate from this currency to the target currency.
     *
     * @param targetCurrency The target currency.
     * @return The exchange rate.
     * @throws IllegalArgumentException if the target currency is not supported.
     */
    public BigDecimal getExchangeRate(Currency targetCurrency) {
        return switch (targetCurrency) {
            case USD -> getUSDtoCurrencyRate(this);
            case EUR -> getEURtoCurrencyRate(this);
            case GBP -> getGBPtoCurrencyRate(this);
            case JPY -> getJPYtoCurrencyRate(this);
            case CAD -> getCADtoCurrencyRate(this);
            case PLN -> getPLNtoCurrencyRate(this);
            default -> throw new IllegalArgumentException("Unsupported target currency: " + targetCurrency.name());
        };
    }

    private BigDecimal getUSDtoCurrencyRate(Currency fromCurrency) {
        return switch (fromCurrency) {
            case EUR -> new BigDecimal("1.18");
            case GBP -> new BigDecimal("1.38");
            case JPY -> new BigDecimal("0.009");
            case CAD -> new BigDecimal("0.78");
            case PLN -> new BigDecimal("0.27");
            default -> throw new IllegalArgumentException("Unsupported currency: " + fromCurrency.name());
        };
    }

    private BigDecimal getPLNtoCurrencyRate(Currency toCurrency) {
        return switch (toCurrency) {
            case USD -> new BigDecimal("3.67"); // PLN to USD exchange rate
            case EUR -> new BigDecimal("4.32"); // PLN to EUR exchange rate
            case GBP -> new BigDecimal("4.97"); // PLN to GBP exchange rate
            case JPY -> new BigDecimal("0.035"); // PLN to JPY exchange rate
            case CAD -> new BigDecimal("2.99"); // PLN to CAD exchange rate
            default -> throw new IllegalArgumentException("Unsupported currency: " + toCurrency.name());
        };
    }

    private BigDecimal getGBPtoCurrencyRate(Currency toCurrency) {
        return switch (toCurrency) {
            case USD -> new BigDecimal("1.39"); // GBP to USD exchange rate
            case EUR -> new BigDecimal("1.17"); // GBP to EUR exchange rate
            case JPY -> new BigDecimal("140.00"); // GBP to JPY exchange rate
            case CAD -> new BigDecimal("1.70"); // GBP to CAD exchange rate
            case PLN -> new BigDecimal("5.03"); // GBP to PLN exchange rate
            default -> throw new IllegalArgumentException("Unsupported currency: " + toCurrency.name());
        };
    }

    private BigDecimal getJPYtoCurrencyRate(Currency toCurrency) {
        return switch (toCurrency) {
            case USD -> new BigDecimal("110.00"); // JPY to USD exchange rate
            case EUR -> new BigDecimal("130.00"); // JPY to EUR exchange rate
            case GBP -> new BigDecimal("0.007"); // JPY to GBP exchange rate
            case CAD -> new BigDecimal("85.00"); // JPY to CAD exchange rate
            case PLN -> new BigDecimal("29.00"); // JPY to PLN exchange rate
            default -> throw new IllegalArgumentException("Unsupported currency: " + toCurrency.name());
        };
    }

    private BigDecimal getEURtoCurrencyRate(Currency toCurrency) {
        return switch (toCurrency) {
            case USD -> new BigDecimal("1.18"); // EUR to USD exchange rate
            case GBP -> new BigDecimal("0.86"); // EUR to GBP exchange rate
            case JPY -> new BigDecimal("140.00"); // EUR to JPY exchange rate
            case CAD -> new BigDecimal("1.43"); // EUR to CAD exchange rate
            case PLN -> new BigDecimal("4.19"); // EUR to PLN exchange rate
            default -> throw new IllegalArgumentException("Unsupported currency: " + toCurrency.name());
        };
    }

    private BigDecimal getCADtoCurrencyRate(Currency toCurrency) {
        return switch (toCurrency) {
            case USD -> new BigDecimal("1.27"); // CAD to USD exchange rate
            case EUR -> new BigDecimal("0.70"); // CAD to EUR exchange rate
            case GBP -> new BigDecimal("0.59"); // CAD to GBP exchange rate
            case JPY -> new BigDecimal("12.00"); // CAD to JPY exchange rate
            case PLN -> new BigDecimal("3.40"); // CAD to PLN exchange rate
            default -> throw new IllegalArgumentException("Unsupported currency: " + toCurrency.name());
        };
    }

    @Override
    public String toString() {
        return displayName;
    }
}

