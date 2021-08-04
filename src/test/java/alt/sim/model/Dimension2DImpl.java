package alt.sim.model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class Dimension2DImpl extends javafx.geometry.Dimension2D {

    private static final double HALF = 2;

    /**
     *
     * @param width the width of Dimension2D object
     * @param height the height of Dimension2D object
     */
    public Dimension2DImpl(final double width, final double height) {
        super(width, height);
    }

    /**
     * Calculates the area of the rectangle.
     * @return the calculated area
     */
    public double computeArea() {
        return super.getHeight() * super.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    public Dimension2D getDimension2D() {
        return new Dimension2DImpl(super.getWidth(), super.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    public Point2D computeCenter() {
        return new Point2D(super.getWidth() / HALF, super.getHeight() / HALF);
    }
}
