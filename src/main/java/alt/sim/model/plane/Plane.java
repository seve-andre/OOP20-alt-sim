package alt.sim.model.plane;

import alt.sim.model.ImageClassification;
import alt.sim.model.LandingAnimation;
import alt.sim.model.calculation.Sprite;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
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
    private boolean isPlaneSelectedForBeenMoved;

    // Section Plane-Animation:
    private LandingAnimation landingAnimation;

    public Plane(final String urlImagePlane) {
       this.isPlaneSelectedForBeenMoved = false;
       this.spritePlane = new Sprite(urlImagePlane, true);

       // Initialize Animation
       this.landingAnimation = new LandingAnimation(this.getImagePlane());

       // Setting Handler for MouseClick STRATEGY da implementare
       setOnClick();
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

    public void setOnClick() {
        this.getImagePlane().setOnMousePressed(event -> { 
            setSpritePlane("images/map_components/airplaneSelected.png");
            isPlaneSelectedForBeenMoved = true;
        });
        this.getImagePlane().setOnMouseReleased(event -> this.getImagePlane().setImage(new Image("images/map_components/airplane.png")));
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

    public void setSpritePlane(final String newUrlImage) {
        this.spritePlane.getImageSpriteResized().setImageSprite(newUrlImage);
    }

    public ScaleTransition getLandingAnimation() {
        return landingAnimation.getLandingAnimation();
    }

    public void setPlaneRotate(final double rotateValue) {
        this.spritePlane.getImageSpriteResized().getImageSprite().setRotate(rotateValue);
    }

    public void setIsPlaneSelectedForBeenMoved(final boolean isPlaneSelectedForBeenMoved) {
        this.isPlaneSelectedForBeenMoved = isPlaneSelectedForBeenMoved;
    }

    public boolean getIsPlaneSelectedForBeenMoved() {
        return this.isPlaneSelectedForBeenMoved;
    }

    @Override
    public String toString() {
        return ("Plane [type=" + type + ", status=" + status + "]");
    }

}
