package alt.sim.model.plane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import alt.sim.model.ClearingPathTest;
import alt.sim.model.ImageClassification;
import alt.sim.model.ExplosionAnimation;
import alt.sim.model.LandingAnimation;
import alt.sim.model.calculation.Sprite;
import alt.sim.view.TransitionTest;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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

    private boolean isPlaneSelectedForBeenMoved;
    private boolean followingPath;

    private TransitionTest controllerTransition;
    private ClearingPathTest controllerCleaning;

    // Section Plane-Animation:
    private LandingAnimation landingAnimation;
    private ExplosionAnimation explosionAnimation;
    private PathTransition transition;
    private PathTransition randomTransition;
    private Path path;

    private List<Point2D> linesPath;
    private List<Point2D> linesPathToRemove;

    // Testing Line saving

    public Plane(final String urlImagePlane) {
       linesPath = new ArrayList<>();
       linesPathToRemove = new ArrayList<>();
       this.isPlaneSelectedForBeenMoved = false;
       this.followingPath = false;
       this.spritePlane = new Sprite(urlImagePlane, true);

       // Initialize Animation
       this.landingAnimation = new LandingAnimation(this.getImagePlane());
       this.explosionAnimation = new ExplosionAnimation(new Point2D(500, 500));
       this.randomTransition = new PathTransition();

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

    public void loadPlaneMovementAnimation() {
        //this.setIsPlaneSelectedForBeenMoved(true);
        this.followingPath = true;
        transition = new PathTransition();
        double pathLenght = 0;
        final double velocityMovement = 0.005;
        double duration = 0;

        // aggiornare le coordinate da richiamare prima di questo metodo
        copyCoordinatesInPath();

        if (transition.getStatus() == Status.RUNNING) {
            this.transition.stop();
            linesPath.clear();
            controllerTransition.clearMap();
            controllerTransition.restoreLinesRemoved();
        }

        transition.setPath(path);
        transition.setNode(this.getImagePlane());
        transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        // Cambiare la velocità a seconda del percoso:
        pathLenght = linesPath.size();
        duration = pathLenght / velocityMovement;
        transition.setDuration(Duration.millis(duration));

        transition.setOnFinished(event -> {
            linesPath.clear();
            controllerTransition.clearMap();
            controllerTransition.restoreLinesRemoved();
            //this.setIsPlaneSelectedForBeenMoved(false);
            this.followingPath = false;
        });
    }

    public ExplosionAnimation getKeyFrameTest() {
        return this.explosionAnimation;
    }

    public boolean isFollowingPath() {
        return this.followingPath;
    }

    public void startPlaneMovementAnimation() {
        this.transition.play();
    }

    public void stopPlaneMovementAnimation() {
        this.transition.stop();
    }

    public String getStatusMovementAnimation() {
        try {
            if (this.transition == null) {
                return ("WAITING");
            } else if (this.transition.getStatus() == Status.STOPPED) {
                return ("WAITING");
            }
        } catch (Exception e) { }
        return this.transition.getStatus().toString();
    }

    public PathTransition getPlaneMovementAnimation() {
        return this.transition;
    }

    public void loadRandomTransition() {
        PauseTransition pauseFinish = new PauseTransition();
        //this.setIsPlaneSelectedForBeenMoved(true);
        this.followingPath = true;

        randomTransition = new PathTransition();
        Path pathRandom = new Path();
        Random r = new Random();

        int randomX = r.nextInt(1400);
        int randomY = r.nextInt(1000);

        pathRandom.getElements().add(new MoveTo(this.getImagePlane().getBoundsInParent().getCenterX(), this.getImagePlane().getBoundsInParent().getCenterY()));
        pathRandom.getElements().add(new LineTo(randomX, randomY));

        // Set a Pause when randomTransition finish
        pauseFinish.setDuration(Duration.seconds(1));

        randomTransition.setPath(pathRandom);
        randomTransition.setNode(this.getImagePlane());
        randomTransition.setDuration(Duration.seconds(10));
        randomTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        randomTransition.play();
        //randomTransition.setOnFinished(event -> this.setIsPlaneSelectedForBeenMoved(false));
        randomTransition.setOnFinished(event -> pauseFinish.play());

        pauseFinish.setOnFinished(event -> followingPath = false);
    }

    public void startRandomTransition() {
        this.randomTransition.play();

    }

    public void stopRandomTransition() {
        this.randomTransition.stop();
    }

    public String getStatusRandomTransition() {
        if (randomTransition.getStatus() == Status.RUNNING) {
            return ("RUNNING-RANDOM");
        }

        return ("STOPPED");
    }

    public PathTransition getRandomTransition() {
        return this.randomTransition;
    }

    private void copyCoordinatesInPath() {
        // Ripuliamo le coordinate presenti dal path prima
        this.path = new Path();

        for (int k = 0; k < this.linesPath.size(); k++) {

            if (k == 0) {
                path.getElements().add(new MoveTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
            } else {
                path.getElements().add(new LineTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
            }
        }
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

            if (controllerTransition.isMoreThanOneSelected()) {
                controllerTransition.clearPlaneSelectedForBeenMoved();
                isPlaneSelectedForBeenMoved = true;
            }

            //System.out.println("Plane " + this.hashCode() + " selected for beenMoved");
        });

        this.getImagePlane().setOnMouseReleased(event -> {
            this.getImagePlane().setImage(new Image("images/map_components/airplane.png"));
        });
    }

    public void setPlaneLinesPath(final List<Point2D> linesPath) {
        if (this.getIsPlaneSelectedForBeenMoved()) {
            this.linesPath.clear();

            for (Point2D lines:linesPath) {
                this.linesPath.add(new Point2D(lines.getX(), lines.getY()));
            }
        }
    }

    public void resetPlaneLinesPath() {
        this.linesPath.clear();
    }

    public void resetPlaneLinesPath(final int idPlane) {
        resetPlaneLinesPath();
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
