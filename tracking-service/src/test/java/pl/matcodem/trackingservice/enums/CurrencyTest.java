package pl.matcodem.trackingservice.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import java.math.BigDecimal;

public class CurrencyTest {

    @Test
    public void testConvertToSameCurrency() {
        Currency usd = Currency.USD;
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal convertedAmount = usd.convertTo(amount, usd);

        assertEquals(amount, convertedAmount);
    }

    @Test
    public void testConvertFromUSDToEUR() {
        Currency usd = Currency.USD;
        Currency eur = Currency.EUR;
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal convertedAmount = usd.convertTo(amount, eur);

        assertEquals(new BigDecimal("118.00"), convertedAmount);
    }

    @Test
    public void testConvertFromGBPToUSD() {
        Currency gbp = Currency.GBP;
        Currency usd = Currency.USD;
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal convertedAmount = gbp.convertTo(amount, usd);

        assertEquals(new BigDecimal("138.00"), convertedAmount);
    }

    @Test
    public void testConvertFromPLNToCAD() {
        Currency pln = Currency.PLN;
        Currency cad = Currency.CAD;
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal convertedAmount = pln.convertTo(amount, cad);

        assertEquals(new BigDecimal("33.44"), convertedAmount);
    }

    @Test
    public void testConvertFromJPYToEUR() {
        Currency jpy = Currency.JPY;
        Currency eur = Currency.EUR;
        BigDecimal amount = new BigDecimal("10000.00");
        BigDecimal convertedAmount = jpy.convertTo(amount, eur);

        assertEquals(new BigDecimal("76.92"), convertedAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConversion() {
        Currency eur = Currency.EUR;
        Currency unsupportedCurrency = Currency.CAD; // Unsupported currency
        BigDecimal amount = new BigDecimal("100.00");
        eur.convertTo(amount, unsupportedCurrency);
    }

    @Test
    public void testGetExchangeRateFromUSDToEUR() {
        Currency usd = Currency.USD;
        Currency eur = Currency.EUR;
        BigDecimal exchangeRate = usd.getExchangeRate(eur);

        assertEquals(new BigDecimal("1.18"), exchangeRate);
    }

    @Test
    public void testGetExchangeRateFromEURToUSD() {
        Currency eur = Currency.EUR;
        Currency usd = Currency.USD;
        BigDecimal exchangeRate = eur.getExchangeRate(usd);

        assertEquals(new BigDecimal("0.8474"), exchangeRate);
    }

    @Test
    public void testGetExchangeRateFromGBPToJPY() {
        Currency gbp = Currency.GBP;
        Currency jpy = Currency.JPY;
        BigDecimal exchangeRate = gbp.getExchangeRate(jpy);

        assertEquals(new BigDecimal("20000.00"), exchangeRate);
    }

    @Test
    public void testGetExchangeRateFromPLNToCAD() {
        Currency pln = Currency.PLN;
        Currency cad = Currency.CAD;
        BigDecimal exchangeRate = pln.getExchangeRate(cad);

        assertEquals(new BigDecimal("0.3381"), exchangeRate);
    }
}
