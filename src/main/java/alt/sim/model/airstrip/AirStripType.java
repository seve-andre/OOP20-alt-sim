package alt.sim.model.airstrip;

public enum AirStripType {

    /**
     * Represents a standard airstrip for classic planes.
     */
    CLASSIC("Classic AirStrip"),

    /**
     * Represents a helipad airstrip for helicopters.
     */
    HELIPAD("Helipad Airstrip");

    private final String property;

    /**
     *
     * @param property the property of the airstrip
     */
    AirStripType(final String type) {
        this.property = type;
    }

    /**
     *
     * @return the property of the airstrip
     */
    public String getProperty() {
        return this.property;
    }
}
