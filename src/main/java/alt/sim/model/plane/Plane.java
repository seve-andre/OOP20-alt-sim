package alt.sim.model.plane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import alt.sim.model.ExplosionAnimation;
import alt.sim.model.LandingAnimation;
import alt.sim.model.SpriteType;
import alt.sim.model.calculation.Sprite;
import alt.sim.view.Seaside;
import alt.sim.view.TransitionTest;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Defines the Plane idea
 * There may be more Plane Tipology (Airfighter, AirPlane, Two-Seater Aircraft, ecc. ecc.).
 * <p>
 * Each one with different features:
 * - Velocity
 * - Size
 * - Dimension
 * - ecc. ecc.
 * <p>
 * A Plane can have a State during the Game (Running, Exploded, Landed, ecc.).
 * Each plane must had its own Image
 */

public class Plane {
    private Tipology type;

    private State state;
    private Sprite spritePlane;

    private boolean isPlaneSelectedForBeenMoved;
    private boolean followingPath;

    private TransitionTest controllerTransition;
    private Seaside controllerSeaside;
    //private ClearingPathTest controllerCleaning;

    // Section Plane-Animation:
    private LandingAnimation landingAnimation;
    private ExplosionAnimation explosionAnimation;
    private PathTransition transition;
    private PathTransition randomTransition;
    private Path path;

    private List<Point2D> linesPath;
    private List<Point2D> linesPathToRemove;

    public Plane(final String urlImagePlane) {
        state = State.WAITING;
        linesPath = new ArrayList<>();
        linesPathToRemove = new ArrayList<>();
        this.isPlaneSelectedForBeenMoved = false;
        this.followingPath = false;
        this.spritePlane = new Sprite(urlImagePlane, true);

        // Initialize Animation
        this.landingAnimation = new LandingAnimation(this.getImagePlane());
        this.explosionAnimation = new ExplosionAnimation(new Point2D(500, 500));
        this.randomTransition = new PathTransition();

        // DA DECOMMENTARE!!!
        // Setting Handler for MouseClick STRATEGY da implementare
        setOnClick();
    }

    public Plane(final SpriteType imageClassification) {
        this(imageClassification.getURLImage());
    }

    public Plane(final String urlImagePlane, final Point2D point) {
        this(urlImagePlane);

        this.spritePlane.setX(point.getX());
        this.spritePlane.setY(point.getY());
    }

    /**
     * @param type   defined the Plane tipology.
     * @param state defined the Plane state.
     */
    public Plane(final Tipology type, final State state) {
        this.type = type;
        this.state = state;
    }

    public void loadPlaneMovementAnimation() {
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
            //controllerTransition.clearLinesDrawed();
            //controllerTransition.restoreLinesRemoved();
            controllerSeaside.clearLinesDrawed();
            controllerSeaside.restoreLinesRemoved();
        }

        transition.setPath(path);
        transition.setNode(this.getImagePlane());
        transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        // Cambiare la velocità a seconda del percoso:
        pathLenght = linesPath.size();
        duration = pathLenght / velocityMovement;
        transition.setDuration(Duration.millis(duration));

        System.out.println(this.linesPath);
        transition.play();

        transition.setOnFinished(event -> {
            // DA DECOMMENTARE!!!
            linesPath.clear();
            //controllerTransition.clearLinesDrawed();
            //controllerTransition.restoreLinesRemoved();
            controllerSeaside.clearLinesDrawed();
            controllerSeaside.restoreLinesRemoved();
            this.followingPath = false;
        });
    }

    public ExplosionAnimation getExplosionAnimation() {
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
        } catch (Exception e) {
        }
        return this.transition.getStatus().toString();
    }

    public PathTransition getPlaneMovementAnimation() {
        return this.transition;
    }

    public void loadRandomTransition(final double boundWidth, final double boundHeight) {
        PauseTransition pauseFinish = new PauseTransition();
        this.followingPath = true;

        double planeWidth = this.getImagePlane().getBoundsInParent().getWidth();
        double planeHeight = this.getImagePlane().getBoundsInParent().getHeight();

        randomTransition = new PathTransition();
        Path pathRandom = new Path();
        Random r = new Random();

        // Metodo per calcolo random position
        double randomX = planeWidth * 0.5 + r.nextDouble() * (boundWidth - planeWidth);
        double randomY = planeHeight * 0.5 + r.nextDouble() * (boundHeight - planeHeight);

        pathRandom.getElements().add(new MoveTo(this.getImagePlane().getBoundsInParent().getCenterX(), this.getImagePlane().getBoundsInParent().getCenterY()));
        pathRandom.getElements().add(new LineTo(randomX, randomY));

        // Set a Pause when randomTransition finish
        pauseFinish.setDuration(Duration.seconds(1));

        randomTransition.setPath(pathRandom);
        randomTransition.setNode(this.getImagePlane());
        randomTransition.setDuration(Duration.seconds(10));
        randomTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        randomTransition.play();
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

    public void connectToController(final TransitionTest controllerTransition) {
        this.controllerTransition = controllerTransition;
    }

    public void connectToController(final Seaside controllerSeaside) {
        this.controllerSeaside = controllerSeaside;
    }

    /*
     * public void connetToControllerClaringPathTest(final ClearingPathTest
     * controllerCleaning) { this.controllerCleaning = controllerCleaning; }
     */

    public void setOnClick() {
        this.getImagePlane().setOnMousePressed(event -> {
            setSpritePlane("images/map_components/airplaneSelected.png");
            isPlaneSelectedForBeenMoved = true;

            //Sostituito da controllerSeaside perchè abbiamo spostato il controller sulla classe Seaside
            /*
            if (controllerTransition.isMoreThanOneSelected()) {
                controllerTransition.clearPlaneSelectedForBeenMoved();
                isPlaneSelectedForBeenMoved = true;
            }*/

            if (controllerSeaside.isMoreThanOneSelected()) {
                controllerSeaside.clearPlaneSelectedForBeenMoved();
                isPlaneSelectedForBeenMoved = true;
            }

        });

        this.getImagePlane().setOnMouseReleased(event -> {
            this.getImagePlane().setImage(new Image("images/map_components/airplane.png"));
        });
    }

    //^^^
    // Aggiungo le coordinate campionate nel Plane
    public void setPlaneLinesPath(final List<Point2D> linesPath) {
        if (this.getIsPlaneSelectedForBeenMoved()) {
            this.linesPath.clear();

            for (Point2D lines : linesPath) {
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
        for (Point2D pointToRemove : linesPathToRemove) {
            System.out.println("linesPathToRemove: " + pointToRemove.getX() + " , " + pointToRemove.getY());
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

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return ("Plane [type=" + type + ", status=" + state + "]");
    }

}
