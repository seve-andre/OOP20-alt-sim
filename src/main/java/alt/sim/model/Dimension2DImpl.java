package alt.sim.model;

public class Dimension2DImpl implements Dimension2D {

    private static final double HALF = 2;
    private final double width;
    private final double height;

    /**
     *
     * @param witdh the width of Dimension2D object
     * @param height the height of Dimension2D object
     */
    public Dimension2DImpl(final double witdh, final double height) {
        this.width = witdh;
        this.height = height;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public double getWidth() {
        return this.width;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public double getHeight() {
        return this.height;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public double computeArea() {
        return this.height * this.width;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Dimension2D getDimension2D() {
        return new Dimension2DImpl(this.width, this.height);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Point2D computeCenter() {
        return new Point2DImpl(this.width / HALF, this.height / HALF);
    }

}
