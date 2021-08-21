package alt.sim.model.plane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import alt.sim.Main;
import alt.sim.controller.seaside.SeasideController;
import alt.sim.model.animation.ExplosionAnimation;
import alt.sim.model.animation.LandingAnimation;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.model.sprite.Sprite;
import alt.sim.model.sprite.SpriteType;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
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
    private static final int SPAWNTRANSITION_DURATION = 2500;
    private Sprite spritePlane;
    private ObservableState obsState;
    private SeasideController controllerSeaside;

    // Section Plane-Animation && Spawn:
    private LandingAnimation landingAnimation;
    private ExplosionAnimation explosionAnimation;
    // Section PathTransition:
    private PathTransition userTransition;
    private PathTransition randomTransition;
    private Path path;
    private boolean isPlaneSelectedForBeenMoved;

    private List<Point2D> linesPath;
    private PathTransition spawnTransition;

    public Plane(final String urlImagePlane) {
        this.spritePlane = new Sprite(urlImagePlane);

        this.obsState = new ObservableState(this, State.SPAWNING);
        this.linesPath = new ArrayList<>();
        this.isPlaneSelectedForBeenMoved = false;

        // Initialize Animation
        this.landingAnimation = new LandingAnimation(this.getSprite());
        this.explosionAnimation = new ExplosionAnimation();
        this.randomTransition = new PathTransition();

        // Setting Handler for MouseClick STRATEGY to implement
        setOnClick();
    }

    public Plane(final SpriteType imageClassification) {
        this(imageClassification.getURLImage());
    }

    public Plane() {    }

    /**
     * Terminate alla the Plane animations when the Game is over.
     */
    public void terminateAllAnimations() {
        if (this.userTransition != null) {
            this.userTransition.stop();
            this.userTransition.setNode(null);
        }

        if (this.randomTransition != null) {
            this.randomTransition.stop();
            this.randomTransition.setNode(null);
        }

        obsState.removeListener();
    }

    /**
     * launch of spawn animation of the Plane.
     * @param side the side position when make the Plane spawn (LEFT, RIGHT, BOTTOM, TOP).
     */
    public void playSpawnAnimation(final SpawnLocation side) {
        spawnTransition = new PathTransition();
        Path pathSpawn = new Path();

        Platform.runLater(() -> {
            final int delta = 50;
            final double width = Main.getStage().getWidth();
            final double height = Main.getStage().getHeight();

            final double halfWidth = width / 2.0;
            final double halfHeight = height / 2.0;

            // Insert coordinates PathSpawn from side
            switch (side) {
            case TOP:
                pathSpawn.getElements().add(new MoveTo(halfWidth, -delta));
                pathSpawn.getElements().add(new LineTo(halfWidth, delta));
                break;

            case RIGHT:
                pathSpawn.getElements().add(new MoveTo(width + delta, halfHeight));
                pathSpawn.getElements().add(new LineTo(width - delta, halfHeight));
                break;

            case BOTTOM:
                pathSpawn.getElements().add(new MoveTo(halfWidth, height + delta));
                pathSpawn.getElements().add(new LineTo(halfWidth, height - delta));
                break;

            case LEFT:
                pathSpawn.getElements().add(new MoveTo(-delta, halfHeight));
                pathSpawn.getElements().add(new LineTo(delta, halfHeight));
                break;

            default:
                break;
            }

            spawnTransition.setPath(pathSpawn);
            spawnTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            spawnTransition.setNode(this.getSprite());
            spawnTransition.setDuration(Duration.millis(SPAWNTRANSITION_DURATION));
            spawnTransition.play();

            spawnTransition.setOnFinished(event -> this.setState(State.WAITING));
        });
    }

    /**
     * RandomTransition used by the Plane when it haven't a specified Path to follow.
     * @param boundWidth Width dimension of the Map used for calculate the random X position of RandomMovement.
     * @param boundHeight Height dimension of the Map used for calculate the random Y position of RandomMovement.
     */
    public void loadRandomTransition(final double boundWidth, final double boundHeight) {
        double randomPathLength;
        final double velocityRandomMovement = 0.03;
        double durationRandomTransition;

        Point2D moveFrom = new Point2D(this.getSprite().getBoundsInParent().getCenterX(),
                this.getSprite().getBoundsInParent().getCenterY());

        if (getState() == State.SPAWNING) {
            return;
        }

        double planeWidth = this.getSprite().getBoundsInParent().getWidth();
        double planeHeight = this.getSprite().getBoundsInParent().getHeight();

        randomTransition = new PathTransition();
        Path pathRandom = new Path();

        // Calculation random position
        double randomX = getRandomCoordinate(boundWidth, planeWidth);
        double randomY = getRandomCoordinate(boundHeight, planeHeight);
        Point2D moveTo = new Point2D(randomX, randomY);

        pathRandom.getElements().add(new MoveTo(moveFrom.getX(), moveFrom.getY()));
        pathRandom.getElements().add(new LineTo(randomX, randomY));

        randomTransition.setPath(pathRandom);
        randomTransition.setNode(this.getSprite());
        // Establish a standard speed depending on the length of the path:
        randomPathLength = (moveFrom.distance(moveTo));
        durationRandomTransition = randomPathLength / velocityRandomMovement;
        randomTransition.setDuration(Duration.millis(durationRandomTransition));
        randomTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        setState(State.RANDOM_MOVEMENT);
        randomTransition.play();

        randomTransition.setOnFinished(event -> {
            if (getState() == State.RANDOM_MOVEMENT) {
                setState(State.WAITING);
            }
        });
    }

    /**
     * @param boundCoordinate coordinate of the Map used to calculate the RandomTransition final position.
     * @param planeCoordinate actual coordinate of the Plane used to set the intial Path of RandomTransition
     * @return the coordinate calculated.
     */
    private double getRandomCoordinate(final double boundCoordinate, final double planeCoordinate) {
        return planeCoordinate * 0.5 + ThreadLocalRandom.current().nextDouble() * (boundCoordinate - planeCoordinate);
    }

    public void loadPlaneMovementAnimation() {
        if (this.getState() == State.SPAWNING) {
            return;
        }

        if (this.randomTransition.getStatus() == Status.RUNNING) {
            this.randomTransition.stop();
        }

        // the Plane is following a Path
        this.userTransition = new PathTransition();

        double pathLenght;
        final double velocityMovement = 0.03;
        double duration;

        // update the coordinate befor call this method
        copyCoordinatesInPath();

        if (userTransition.getStatus() == Status.RUNNING) {
            this.userTransition.stop();
            linesPath.clear();
            controllerSeaside.clearLinesDrawed();
            controllerSeaside.restoreLinesRemoved();
        }

        userTransition.setPath(path);
        userTransition.setNode(this.getSprite());
        userTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        // Stablished a standard velocity depended on Pathlenght.
        pathLenght = (linesPath.get(0).distance(linesPath.get(linesPath.size() - 1)));
        duration = pathLenght / velocityMovement;
        userTransition.setDuration(Duration.millis(duration));

        setState(State.USER_MOVEMENT);
        userTransition.play();

        userTransition.setOnFinished(event -> {
            linesPath.clear();
            controllerSeaside.clearLinesDrawed();
            controllerSeaside.restoreLinesRemoved();
            setState(State.WAITING);
        });
    }

    /**
     * @return the SpawnTransition animation ready to played.
     */
    public PathTransition getSpawnTransition() {
        return this.spawnTransition;
    }

    /**
     * @return the ExplosionAnimation ready to played.
     */
    public ExplosionAnimation getExplosionAnimation() {
        return this.explosionAnimation;
    }

    public void stopPlaneMovementAnimation() {
        if (this.userTransition != null) {
            this.userTransition.stop();
        }
    }

    /**
     * @return the Movement status of the Plane Animation.
     */
    public String getStatusMovementAnimation() {
        try {
            if (this.userTransition == null || this.userTransition.getStatus() == Status.STOPPED) {
                return ("WAITING");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.userTransition.getStatus().toString();
    }

    /**
     * @return the PathTransition animation ready to played.
     */
    public PathTransition getPlaneMovementAnimation() {
        return this.userTransition;
    }

    public void stopRandomTransition() {
        if (this.randomTransition != null) {
            this.randomTransition.stop();
        }
    }

    /**
     * @return the RandomTransition animation ready to played.
     */
    public PathTransition getRandomTransition() {
        return this.randomTransition;
    }

    private void copyCoordinatesInPath() {
        // Cleaning the coordinates presented
        this.path = new Path();

        for (int k = 0; k < this.linesPath.size(); k++) {

            if (linesPath.get(k).getX() > 0 && linesPath.get(k).getY() > 0) {
                if (k == 0) {
                    path.getElements().add(new MoveTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
                } else {
                    path.getElements().add(new LineTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
                }
            }
        }
    }

    /**
     *
     * @param controllerSeaside controlled to link with this class.
     */
    public void connectToController(final SeasideController controllerSeaside) {
        this.controllerSeaside = controllerSeaside;
    }

    /**
     *     Managed the Plane image when is clicked by the Mouse Plane --> PlaneSelected.
     */
    public void setOnClick() {
        this.getSprite().setOnMousePressed(event -> {
            setSpritePlane(SpriteType.AIRPLANE_SELECTED.getURLImage());
            isPlaneSelectedForBeenMoved = true;

            if (controllerSeaside.isMoreThanOneSelected()) {
                controllerSeaside.clearPlaneSelectedForBeenMoved();
                isPlaneSelectedForBeenMoved = true;
            }

        });

        this.getSprite().setOnMouseReleased(event -> this.getSprite().setImage(new Image(SpriteType.AIRPLANE.getURLImage())));
    }

    /**
     * @param linesPath coordinates sampled added into Plane.
     */
    public synchronized void setPlaneLinesPath(final List<Point2D> linesPath) {
        List<Point2D> linesPathClear;

        if (this.isPlaneSelectedForBeenMoved()) {
            this.linesPath.clear();

            linesPathClear = removeDuplicateInLinesPath(linesPath);

            for (Point2D linesClear:linesPathClear) {
                //System.out.println("coordinate passate dopo: " + linesClear.getX() + " , " + linesClear.getY());
                this.linesPath.add(new Point2D(linesClear.getX(), linesClear.getY()));
            }
        }
    }

    //TODO Test duplicate coordinates
    /**
     * @param linesPath list of coordinated sampled.
     * @return a list of coordinated cleaned from duplicated.
     */
    public List<Point2D> removeDuplicateInLinesPath(final List<Point2D> linesPath) {
        double xCopy;
        double yCopy;

        for (int k = 0; k < linesPath.size(); k++) {
            xCopy = linesPath.get(k).getX();
            yCopy = linesPath.get(k).getY();

            for (int j = 0; j < linesPath.size(); j++) {
                if (j != k && linesPath.get(j).getX() == xCopy && linesPath.get(j).getY() == yCopy) {
                    linesPath.remove(j);
                    j--;
                }
            }
        }
        return linesPath;
    }

    /**
     * @return the coordinate catched.
     */
    public synchronized List<Point2D> getPlaneLinesPath() {
        return this.linesPath;
    }

    /**
     * @return the ImageView of the Plane object.
     */
    public ImageView getSprite() {
        return this.spritePlane.getSprite();
    }

    /**
     * @param newUrlImage the new Image to set into Plane
     */
    public void setSpritePlane(final String newUrlImage) {
        this.spritePlane.setSpritePlane(newUrlImage);
    }

    /**
     * @return the LandingAnimation ready to played.
     */
    public ScaleTransition getLandingAnimation() {
        return landingAnimation.getLandingAnimation();
    }

    /**
     * @param isPlaneSelectedForBeenMoved set the boolean value to Plane specified if was selected for moved by User.
     */
    public void setIsPlaneSelectedForBeenMoved(final boolean isPlaneSelectedForBeenMoved) {
        this.isPlaneSelectedForBeenMoved = isPlaneSelectedForBeenMoved;
    }

    /**
     * @return the actual value of planeSelectedForBeenMoved.
     */
    public boolean isPlaneSelectedForBeenMoved() {
        return this.isPlaneSelectedForBeenMoved;
    }

    /**
     * @return the actual State of Plane.
     */
    public synchronized State getState() {
        return obsState.getState();
    }

    /**
     * @param state value of Plane State during the Game.
     */
    public synchronized void setState(final State state) {
        this.obsState.setState(state);
    }

    public void removedObservableStateListener() {
        this.obsState.removeListener();
    }

    /**
     * @return the check condition about Plane landing.
     */
    public boolean isLanded() {
        return obsState.getState() == State.LANDED;
    }
}
