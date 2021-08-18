package alt.sim.model.plane;

/**
 * Enum that defines the types of Plane that can be in game,
 * each type of Plane will have different characteristics as in reality,
 * a Fighter  is faster than an Airliner.
 */
public enum PlaneType {

    /**
     *  Defines the typology that a Plane has, with different values of (velocity, lenght, width)
     *  Two_seater Plane: not fast, but slim.
     */
    TWO_SEATER(1.5),

    /**
     * Medium Plane.
     */
    AIRPLANE(1),

    /**
     * Airplane: classic civil big Plane: very heavy and slow.
     */
    AIRLINER(0.5),

    /**
     * Military plane: the fastest among the Planes.
     */
    FIGHTER(3);

    private final double velocity;

    /**
     * @param velocity defined the Plane velocity.
    */
    PlaneType(final double velocity) {
        this.velocity = velocity;
    }

    /**
     * @return Plane velocity.
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * @return summary informations of Tipology class.
     */
    @Override
    public String toString() {
        return this.name() + ":" + " velocity: " + this.velocity;
    }
}
