package alt.sim.model.plane;

import java.io.IOException;

import alt.sim.model.ExplosionAnimation;
import alt.sim.model.ImageClassification;
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
    private ExplosionAnimation explosionAnimation;


    public Plane(final String urlImagePlane) {
       this.spritePlane = new Sprite(urlImagePlane, true);
       this.explosionAnimation = new ExplosionAnimation(this);
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
        ScaleTransition landingAnimation = new ScaleTransition();

        //Setting the duration for the transition 
        landingAnimation.setDuration(Duration.millis(2000)); 

        //Setting the node for the transition 
        landingAnimation.setNode(this.getImagePlane()); 

        //Setting the final dimensions for scaling 
        landingAnimation.setToX(0);
        landingAnimation.setToY(0); 

        //Setting the cycle count for the translation 
        landingAnimation.setCycleCount(1); 

        //Setting auto reverse value to true 
        landingAnimation.setAutoReverse(false); 

        return landingAnimation;
    }

    public void getExplosionAnimation() throws IOException {
        //explosionAnimation.getExplosionAnimation(this.getImagePlane());
        //Setting faded transition in Animation Explosion
        ScaleTransition scaleExplosionAnimation = new ScaleTransition();

        scaleExplosionAnimation.setNode(this.getImagePlane());

        //Setting the final dimensions for scaling 
        scaleExplosionAnimation.setFromX(0);
        scaleExplosionAnimation.setFromY(0);
        scaleExplosionAnimation.setToX(1);
        scaleExplosionAnimation.setToY(1);

        scaleExplosionAnimation.setAutoReverse(true);
        scaleExplosionAnimation.setCycleCount(2);
        scaleExplosionAnimation.setDuration(Duration.millis(600));

        scaleExplosionAnimation.play();
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
