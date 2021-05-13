package alt.sim.model.plane;

/**
 * Enum that defines the types of Plane that can be in game,
 * each type of Plane will have different characteristics as in reality,
 * a Fighter  is faster than an Airliner.
 */
public enum Tipology {

    /**
     *  Defines the typology that a Plane has, with different values of (velocity, lenght, width)
     *  Two_seater Plane: not fast, but slim.
     */
    TWO_SEATER(1.5, 7.16, 10.87),

    /**
     * Medium Plane.
     */
    AIRPLANE(1, 19.65, 29),

    /**
     * Airplane: classic civil big Plane: very heavy and slow.
     */
    AIRLINER(0.5, 73, 64),

    /**
     * Military plane: the fastest among the Planes.
     */
    FIGHTER(3, 15, 9.96);

    private double velocity;
    private double lenght;
    private double width;

    /**
     * @param velocity defined the Plane velocity.
     * @param lenght indicated the length of Plane.
     * @param width indicated the width of Plane.
    */
    Tipology(final double velocity, final double lenght, final double width) {
        this.velocity = velocity;
        this.lenght = Math.floor(lenght);
        this.width = Math.floor(width);
    }

    /**
     * @return Plane velocity.
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * @return Plane lenght.
     */
    public double getLenght() {
        return this.lenght;
    }

    /**
     * @return Plane width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return summary informations of Tipology class.
     */
    @Override
    public String toString() {
        return this.name() + ":" + " velocity: " + this.velocity + " lenght: " + this.lenght + " width: " + this.width;
    }
}
