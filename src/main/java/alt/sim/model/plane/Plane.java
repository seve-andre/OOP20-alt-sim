package alt.sim.model.plane;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.ClearingPathTest;
import alt.sim.model.ImageClassification;
import alt.sim.model.LandingAnimation;
import alt.sim.model.calculation.Sprite;
import alt.sim.view.TransitionTest;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

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
    private TransitionTest controllerTransition;
    private ClearingPathTest controllerCleaning;

    // Section Plane-Animation:
    private LandingAnimation landingAnimation;

    private List<Point2D> linesPath;
    private List<Point2D> linesPathToRemove;

    // Testing Line saving

    public Plane(final String urlImagePlane) {
       linesPath = new ArrayList<>();
       linesPathToRemove = new ArrayList<>();
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

    public void connetToController(final TransitionTest controllerTransition) {
        this.controllerTransition = controllerTransition;
    }

    public void connetToControllerClaringPathTest(final ClearingPathTest controllerCleaning) {
        this.controllerCleaning = controllerCleaning;
    }

    public void setOnClick() {
        this.getImagePlane().setOnMousePressed(event -> {
            setSpritePlane("images/map_components/airplaneSelected.png");
            isPlaneSelectedForBeenMoved = true;
        });
        this.getImagePlane().setOnMouseReleased(event -> {
            this.getImagePlane().setImage(new Image("images/map_components/airplane.png"));
        });
    }

    public void setPlaneLinesPath(final List<Point2D> linesPath) {
        // Eliminiamo la possibilità di cancellare da Plane per vedere il funzionamento    
        //controllerCleaning.clearCanvasLines(this.linesPathToRemove);

            this.linesPath = linesPath;
            //this.linesPathToRemove = linesPath;
            /*
             * for (Point2D point:linesPath) { System.out.println("linesPath: " +
             * point.getX() + " , " + point.getY()); }
             * 
             * System.out.println("");
             */

            /*
             * for (Point2D pointToRemove:linesPathToRemove) {
             * System.out.println("linesPathToRemove: " + pointToRemove.getX() + " , " +
             * pointToRemove.getY()); }
             */
    }

    public List<Point2D> getPlaneLinesPath() {
        //System.out.println("getPlaneLinesPath in Plane = " + this.linesPath);
        return this.linesPath;
    }

    public List<Point2D> getPlaneLinesPathToRemove() {
        return this.linesPathToRemove;
    }

    public void setPlaneLinesPathToRemove(final List<Point2D> linesPathToRemove) {
        for (Point2D pointToRemove:linesPathToRemove) {
            //System.out.println("linesPathToRemove: " + pointToRemove.getX() + " , " + pointToRemove.getY());
        }

        this.linesPathToRemove = linesPathToRemove;
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
