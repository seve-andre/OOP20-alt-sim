package alt.sim.model.airstrip;

/**
 *
 * An enum describing the status of the airstrip.
 *
 */
public enum AirStripStatus {

    /**
     * The airstrip is free and ready for landing planes.
     */
    FREE("Ready for planes"),

    /**
     * The airstrip is busy because a plane is landing right now.
     */
    BUSY("A plane is landing...");

    private String status;

    /**
     *
     * @param status the status of the airstrip
     */
    AirStripStatus(final String status) {
        this.status = status;
    }

    /**
     * getter method for the enum property.
     * @return the status of the airstrip
     */
    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "AirStripStatus= " + this.status;
    }
}
