package alt.sim.model.plane;


/** 
 * Enum describing the states a Plane can have during the game.
*/
public enum State {
    /** state of the Plane object during is execution: (0) the Plane is Spawned.  */
    SPAWNED("The Plane is Spawned", 0),
    /** (1) is moving into the Map. */
    MOVING("The Plane is on movement", 1),
    /** (2) is exploded after a collision.  */
    EXPLODED("The plane is Exploded", 2),
    /** is landing at the AirStrip. */
    LANDED("The plane is on Landing", 3);

    private String statusOnRun;
    private int idStatus;

    /**
     * @param statusOnRun indica lo stato attuale del Plane.
     * @param ID_Status indica l'ID del Plane.
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
    public String toString() {
        return (this.statusOnRun + " ID: " + this.idStatus);
    }
}
