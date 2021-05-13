package alt.sim.model.plane;


/**
 * Enum describing the states a Plane can have during the game.
*/
public enum State {

    /**
     * (0) Plane is Spawned.
     */
    SPAWNED("spawned", 0),

    /**
     * (1) Plane is moving into the Map.
     */
    MOVING("moving", 1),

    /**
     * (2) Plane has exploded after a collision.
     */
    EXPLODED("exploded", 2),

    /**
     * (3) Plane is landing at the AirStrip.
     */
    LANDED("landing", 3);

    private String statusOnRun;
    private int idStatus;

    /**
     * @param statusOnRun indicates Plane current state.
     * @param idStatus indicates Plane id related to status.
    */
    State(final String statusOnRun, final int idStatus) {
        this.statusOnRun = statusOnRun;
        this.idStatus = idStatus;
    }

    /**
     * @return the actual status of Plane.
     */
    public String getStatusOnRun() {
        return this.statusOnRun;
    }

    /**
     * @return the id-code of the status.
    */
    public int getIdStatus() {
        return this.idStatus;
    }

    /**
     * @return summary informations method of State class.
     */
    @Override
    public String toString() {
        return "Status=" + this.statusOnRun + " ID: " + this.idStatus;
    }
}
