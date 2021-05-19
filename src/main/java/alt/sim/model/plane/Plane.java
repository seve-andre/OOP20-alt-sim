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

    @Override
    public String toString() {
        return "Plane [type=" + type + ", status=" + status + "]";
    }
}
