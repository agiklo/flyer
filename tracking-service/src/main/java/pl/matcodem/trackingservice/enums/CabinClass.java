package pl.matcodem.trackingservice.enums;

import java.math.BigDecimal;

/**
 * An enum representing different aircraft classes.
 */
public enum CabinClass {
    BUSINESS("Business Class", 2, true, true, new BigDecimal("5000.00")),
    ECONOMY("Economy Class", 3, false, false, new BigDecimal("1000.00")),
    FIRST("First Class", 1, true, true, new BigDecimal("10000.00"));

    private final String displayName;
    private final int numberOfSeatsPerRow;
    private final boolean hasInFlightEntertainment;
    private final boolean includesMeals;
    private final BigDecimal additionalCost;

    /**
     * Constructor for AircraftClass.
     *
     * @param displayName              The display name of the class.
     * @param numberOfSeatsPerRow      The number of seats per row in the aircraft's cabin.
     * @param hasInFlightEntertainment  Indicates whether this class provides in-flight entertainment.
     * @param includesMeals            Indicates whether meals are included in this class.
     * @param additionalCost           The additional cost associated with upgrading to this class.
     */
    CabinClass(String displayName, int numberOfSeatsPerRow, boolean hasInFlightEntertainment, boolean includesMeals, BigDecimal additionalCost) {
        this.displayName = displayName;
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
        this.hasInFlightEntertainment = hasInFlightEntertainment;
        this.includesMeals = includesMeals;
        this.additionalCost = additionalCost;
    }

    /**
     * Get the display name of the aircraft class.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the number of seats per row in the aircraft's cabin.
     *
     * @return The number of seats per row.
     */
    public int getNumberOfSeatsPerRow() {
        return numberOfSeatsPerRow;
    }

    /**
     * Check if this class has in-flight entertainment.
     *
     * @return True if it has in-flight entertainment, false otherwise.
     */
    public boolean hasInFlightEntertainment() {
        return hasInFlightEntertainment;
    }

    /**
     * Check if this class includes meals.
     *
     * @return True if it includes meals, false otherwise.
     */
    public boolean includesMeals() {
        return includesMeals;
    }

    /**
     * Get the additional cost associated with upgrading to this class.
     *
     * @return The additional cost.
     */
    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    /**
     * Calculate the upgrade cost for a given number of passengers.
     *
     * @param numberOfPassengers The number of passengers to calculate the cost for.
     * @return The upgrade cost.
     * @throws IllegalArgumentException if the number of passengers is negative.
     */
    public BigDecimal calculateUpgradeCost(int numberOfPassengers) {
        if (numberOfPassengers < 0) {
            throw new IllegalArgumentException("Number of passengers cannot be negative.");
        }
        return additionalCost.multiply(new BigDecimal(numberOfPassengers));
    }

    /**
     * Get the AircraftClass enum constant based on its display name.
     *
     * @param displayName The display name to look up.
     * @return The corresponding AircraftClass constant.
     * @throws IllegalArgumentException if the display name is not found.
     */
    public static CabinClass getClassByDisplayName(String displayName) {
        for (CabinClass aircraftClass : values()) {
            if (aircraftClass.displayName.equals(displayName)) {
                return aircraftClass;
            }
        }
        throw new IllegalArgumentException("Invalid display name: " + displayName);
    }

    /**
     * Check if this class is luxurious.
     *
     * @return True if it's luxurious (Business or First Class), false otherwise.
     */
    public boolean isLuxurious() {
        return this == BUSINESS || this == FIRST;
    }

    /**
     * Check if this class is economical.
     *
     * @return True if it's economical (Economy Class), false otherwise.
     */
    public boolean isEconomical() {
        return this == ECONOMY;
    }

    /**
     * Get a description of the aircraft class, including its display name and the number of seats per row.
     *
     * @return The class description.
     */
    public String getDescription() {
        return "Aircraft class: " + displayName + ", Seats per row: " + numberOfSeatsPerRow;
    }
}
