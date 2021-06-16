package alt.sim.model.plane;

import java.io.IOException;

import alt.sim.model.ExplosionAnimation;
import alt.sim.model.ImageClassification;
import alt.sim.model.LandingAnimation;
import alt.sim.model.calculation.Sprite;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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

    // Section Plane-Animation:
    private ExplosionAnimation explosionAnimation;
    private LandingAnimation landingAnimation;


    public Plane(final String urlImagePlane) {
       this.spritePlane = new Sprite(urlImagePlane, true);

       // Initialize Animation
       this.explosionAnimation = new ExplosionAnimation(new Point2D(20, 50));
       this.landingAnimation = new LandingAnimation(new Point2D(20, 50), this.getImagePlane());
    }

    public Plane(final ImageClassification imageClassification) {
        this(imageClassification.getURLImage());
    }

    public Plane(final String urlImagePlane, final Point2D point) {
        this(urlImagePlane);

        this.spritePlane.setX(point.getX());
        this.spritePlane.setY(point.getY());
    }

    /**
     * @param type defined the Plane tipology.
     * @param status defined the Plane state.
     */
    public Plane(final Tipology type, final State status) {
        this.type = type;
        this.status = status;
    }

    /**
     * @return the ImageView of the Plane object.
     */
    public ImageView getImagePlane() {
        //initializing the Sprite object
        return this.spritePlane.getImageSpriteResized().getImageSprite();
    }

    public Sprite getSpritePlane() {
        return this.spritePlane;
    }

    public ScaleTransition getLandingAnimation() {
        return landingAnimation.getLandingAnimation();
    }

    public void getExplosionAnimation() throws IOException {
        explosionAnimation.getExplosionAnimation().start();
    }

    public void setPlaneRotate(final double rotateValue) {
        this.spritePlane.getImageSpriteResized().getImageSprite().setRotate(rotateValue);
    }

    @Override
    public String toString() {
        return ("Plane [type=" + type + ", status=" + status + "]");
    }

}
