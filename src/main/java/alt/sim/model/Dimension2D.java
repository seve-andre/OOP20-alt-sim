package alt.sim.model;

public interface Dimension2D {
    /**
     *
     * @return the width of the Dimension2D object
     */
    double getWidth();
    /**
     *
     * @return the height of the Dimension2D object
     */
    double getHeight();
    /**
     *
     * @return the area of the Dimension2D object
     */
    double computeArea();
    /**
     *
     * @return the Dimension2D object itself
     */
    double getDimension2D();
    /**
     *
     * @return a point2D witch corresponds to the center of the Point2D
     */
    Point2D computeCenter();
}
