package alt.sim.model;

public class Point2DImpl implements Point2D {

    private final double x;
    private final double y;

    /**
     *
     * @param x the x position of Point2D
     * @param y the y position of Point2D
     */
    public Point2DImpl(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public double getX() {
        return this.x;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public double getY() {
        return this.y;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Point2D getPoint2D() {
        return new Point2DImpl(this.x, this.y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point2DImpl other = (Point2DImpl) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Point2DImpl [x=" + x + ", y=" + y + "]";
    }
}
