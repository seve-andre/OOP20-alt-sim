package alt.sim.model.plane;

import alt.sim.model.animation.ExplosionAnimation;
import alt.sim.model.spawn.SpawnLocation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.List;

public interface Plane {

    /**
     * launch of spawn animation of the Plane.
     * @param side the side position when make the Plane spawn (LEFT, RIGHT, BOTTOM, TOP).
     */
    void playSpawnAnimation(SpawnLocation side);

    /**
     * RandomTransition used by the Plane when it haven't a specified Path to follow.
     * @param boundWidth Width dimension of the Map used for calculate the random X position of RandomMovement.
     * @param boundHeight Height dimension of the Map used for calculate the random Y position of RandomMovement.
     */
    void loadRandomTransition(double boundWidth, double boundHeight);

    /**
     * Load the userTransition movement, that permits at the Plane to follow the Path drawed by User.
     */
    void loadPlaneMovementAnimation();

    /**
     * @return the ExplosionAnimation ready to played.
     */
    ExplosionAnimation getExplosionAnimation();

    /**
     * @return the LandingAnimation ready to played.
     */
    ScaleTransition getLandingAnimation();

    /**
     * @return the ImageView of the Plane object.
     */
    ImageView getSprite();

    /**
     * Managed the Plane image when is clicked by the Mouse Plane --> PlaneSelected.
     */
    void setOnClick();

    /**
     * @param linesPath coordinates sampled added into Plane.
     */
    void setPlaneLinesPath(List<Point2D> linesPath);

    /**
     * @param newUrlImage the new Image to set into Plane.
     */
    void setSpritePlane(String newUrlImage);

    /**
     * removed the ObservableState for terminate the movement when Game terminated.
     */
    void removedObservableStateListener();

    /**
     * @return the actual value of planeSelectedForBeenMoved.
     */
    boolean isPlaneSelectedForBeenMoved();
}
