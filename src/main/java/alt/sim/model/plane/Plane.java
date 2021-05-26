package alt.sim.model.plane;

import alt.sim.model.calculation.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * Defines the Plane idea
 * There may be more Plane Tipology (Airfighter, AirPlane, Two-Seater Aircraft, ecc. ecc.).
 *
 * Each one with different features:
 *      - Velocity
 *      - Size
 *      - Dimension
 *      - ecc. ecc.
 *
 * A Plane can have a State during the Game (Running, Exploded, Landed, ecc.).
 * Each plane must had its own Image
 *
 */

public class Plane {

    private Tipology type;
    private State status;
    private Sprite spritePlane;

    public Plane() {
        this.spritePlane = new Sprite(new Point2D(0, 0));
    }

    public Plane(final String urlImagePlane) {
       this.spritePlane = new Sprite(urlImagePlane);
    }

    public Plane(final String urlImagePlane, final Point2D point) {
        this(urlImagePlane);

        this.spritePlane.setPoint2D(point);
    }

    /**
     * @param type defined the Plane tipology.
     * @param status defined the Plane state.
     */
    public Plane(final Tipology type, final State status) {
        this();
        this.type = type;
        this.status = status;
    }

    /**
     * @return the ImageView of the Plane object.
     */
    public ImageView getImagePlane() {
        //initializing the Sprite object
        return this.spritePlane.getImage();
    }

    public Sprite getSpritePlane() {
        return this.spritePlane;
    }

    public double getX() {
        return this.spritePlane.getX();
    }

    public double getY() {
        return this.spritePlane.getY();
    }

    public void setX(final double x) {
        this.spritePlane.setX(x);
    }

    public void setY(final double y) {
        this.spritePlane.setY(y);
    }

    @Override
    public String toString() {
        return "Plane [type=" + type + ", status=" + status + "]";
    }

}
