package pl.matcodem.trackingservice.enums;


import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CabinClassTest {
    @Test
    public void testGetDisplayName() {
        assertEquals("Business Class", CabinClass.BUSINESS.getDisplayName());
        assertEquals("Economy Class", CabinClass.ECONOMY.getDisplayName());
        assertEquals("First Class", CabinClass.FIRST.getDisplayName());
    }

    @Test
    public void testGetNumberOfSeatsPerRow() {
        assertEquals(2, CabinClass.BUSINESS.getNumberOfSeatsPerRow());
        assertEquals(3, CabinClass.ECONOMY.getNumberOfSeatsPerRow());
        assertEquals(1, CabinClass.FIRST.getNumberOfSeatsPerRow());
    }

    @Test
    public void testHasInFlightEntertainment() {
        assertTrue(CabinClass.BUSINESS.hasInFlightEntertainment());
        assertFalse(CabinClass.ECONOMY.hasInFlightEntertainment());
        assertTrue(CabinClass.FIRST.hasInFlightEntertainment());
    }

    @Test
    public void testIncludesMeals() {
        assertTrue(CabinClass.BUSINESS.includesMeals());
        assertFalse(CabinClass.ECONOMY.includesMeals());
        assertTrue(CabinClass.FIRST.includesMeals());
    }

    @Test
    public void testGetAdditionalCost() {
        assertEquals(new BigDecimal("5000.00"), CabinClass.BUSINESS.getAdditionalCost());
        assertEquals(new BigDecimal("1000.00"), CabinClass.ECONOMY.getAdditionalCost());
        assertEquals(new BigDecimal("10000.00"), CabinClass.FIRST.getAdditionalCost());
    }

    @Test
    public void testCalculateUpgradeCost() {
        assertEquals(new BigDecimal("10000.00"), CabinClass.BUSINESS.calculateUpgradeCost(2));
        assertEquals(new BigDecimal("3000.00"), CabinClass.ECONOMY.calculateUpgradeCost(3));
        assertEquals(new BigDecimal("20000.00"), CabinClass.FIRST.calculateUpgradeCost(2));
    }

    @Test
    public void testCalculateUpgradeCostWithNegativePassengers() {
        try {
            CabinClass.BUSINESS.calculateUpgradeCost(-1);
            fail("Expected IllegalArgumentException for negative passengers");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testGetClassByDisplayName() {
        assertEquals(CabinClass.BUSINESS, CabinClass.getClassByDisplayName("Business Class"));
        assertEquals(CabinClass.ECONOMY, CabinClass.getClassByDisplayName("Economy Class"));
        assertEquals(CabinClass.FIRST, CabinClass.getClassByDisplayName("First Class"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetClassByInvalidDisplayName() {
        CabinClass.getClassByDisplayName("Invalid Class");
    }

    @Test
    public void testIsLuxurious() {
        assertTrue(CabinClass.BUSINESS.isLuxurious());
        assertFalse(CabinClass.ECONOMY.isLuxurious());
        assertTrue(CabinClass.FIRST.isLuxurious());
    }

    @Test
    public void testIsEconomical() {
        assertFalse(CabinClass.BUSINESS.isEconomical());
        assertTrue(CabinClass.ECONOMY.isEconomical());
        assertFalse(CabinClass.FIRST.isEconomical());
    }
}