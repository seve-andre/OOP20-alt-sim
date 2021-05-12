package alt.sim.model.calculation;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class Dimension2DImpl extends javafx.geometry.Dimension2D {

    private static final double HALF = 2;
    /**
     *
     * @param witdh the width of Dimension2D object
     * @param height the height of Dimension2D object
     */
    public Dimension2DImpl(final double witdh, final double height) {
        super(witdh, height);
    }

    /**
     * method that calculates the area of the rectangle.
     * @return the area calculated
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
