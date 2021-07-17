package alt.sim.model.plane;


/**
 * Enum describing the states a Plane can have during the game.
 */
public enum State {

    /**
     * (0) Plane is Spawned.
     */
    SPAWNING,

    /**
     * (1) Plane is moving into the Map.
     */
    MOVING,

    /**
     * (4) Plane waiting to Move.
     */
    WAITING;
}
