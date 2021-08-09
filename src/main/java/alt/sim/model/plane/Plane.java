package alt.sim.model.plane;

import alt.sim.model.animation.ExplosionAnimation;
import alt.sim.model.animation.LandingAnimation;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.model.sprite.SpriteType;
import alt.sim.view.seaside.Seaside;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static Random r = new Random();
    private Tipology type;
    private Sprite spritePlane;
    //private ImageView spritePlane;

    private ObservableState obsState;
    private ObservablePosition obsPosition;
    // DA SISTEMARE
    private Seaside controllerSeaside;

    // Section Plane-Animation && Spawn:
    private LandingAnimation landingAnimation;
    private ExplosionAnimation explosionAnimation;
    // Timer for Plane spawn;
    private Timeline timeline;

    // Section PathTransition:
    private PathTransition userTransition;
    private PathTransition randomTransition;
    private Path path;
    private boolean isPlaneSelectedForBeenMoved;
    private boolean followingPath;

    private List<Point2D> linesPath;
    private List<Point2D> linesPathToRemove;

    // TEST SALVATAGGIO COORDIANTE PLANE
    private double lastPositionX;
    private double lastPositionY;
    private double positionX;
    private double positionY;
    private PathTransition spawnTransition;
    private static List<PathTransition> pathTransitionList = new ArrayList<>();

    //private static final List<SpawnLocation> SPAWN_LOCATIONS;

    public Plane(final String urlImagePlane) {
        /* this.spritePlane = new ImageView(new Image(urlImagePlane));
        // Test Posizione ImageView
        System.out.println(this.spritePlane.getBoundsInParent().getMinX() + " , " + this.spritePlane.getBoundsInParent().getMinY()
                + " /n " +
                this.spritePlane.getBoundsInParent().getMaxX() + " , " + this.spritePlane.getBoundsInParent().getMaxY()
        );*/

        this.spritePlane = new Sprite(urlImagePlane);
        //TODO Test bug flash immagine plane durante partita.

        this.obsState = new ObservableState(this, State.SPAWNING);
        this.linesPath = new ArrayList<>();
        this.linesPathToRemove = new ArrayList<>();
        this.isPlaneSelectedForBeenMoved = false;
        this.followingPath = false;

        // Initialize Animation
        this.landingAnimation = new LandingAnimation(this.getSprite());
        this.explosionAnimation = new ExplosionAnimation(new Point2D(500, 500));
        this.randomTransition = new PathTransition();

        // Setting Handler for MouseClick STRATEGY da implementare
        setOnClick();
    }

    public Plane(final SpriteType imageClassification) {
        this(imageClassification.getURLImage());
    }

    public void terminateAllAnimation() {
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

    public void pauseAllAnimation() {
        if (this.userTransition != null) {
            this.userTransition.pause();
        }

        if (this.randomTransition != null) {
            this.randomTransition.pause();
        }
    }

    public void resumeAllAnimation() {
        if (this.userTransition != null) {
            this.userTransition.play();
        }

        if (this.randomTransition != null) {
            this.randomTransition.play();
        }
    }

    public void playSpawnAnimation(final SpawnLocation side) {

        spawnTransition = new PathTransition();
        Path pathSpawn = new Path();

        Platform.runLater(() -> {
            // Ridimensionamento planeSpawned
            this.getSprite().setFitWidth(64);
            this.getSprite().setFitHeight(64);

            // TEST FUORIBORDO da DECOMMENTARE TERMIANTO IL TEST
            Bounds boundryMap = controllerSeaside.getPane().getBoundsInLocal();

            final int delta = 50;
            final double width = boundryMap.getWidth();
            final double height = boundryMap.getHeight();

            final double halfWidth = width / 2.0;
            final double halfHeight = height / 2.0;

            // Inserimento coordinate PathSpawn from side
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

            // DI TEST PER IL FUORIBORDO, da DECOMMENTARE!!!
            //controllerSeaside.insertPlaneInMap(this);

            spawnTransition.setPath(pathSpawn);
            spawnTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            spawnTransition.setNode(this.getSprite());
            spawnTransition.setDuration(Duration.millis(2500));
            spawnTransition.play();

            spawnTransition.setOnFinished(event -> {
                this.setState(State.WAITING);
            });
        });
    }

    public void loadRandomTransition(final double boundWidth, final double boundHeight) {
        double randomPathLength;
        final double velocityRandomMovement = 0.03;
        double durationRandomTransition;

        Point2D moveFrom = new Point2D(this.getSprite().getBoundsInParent().getCenterX(),
                this.getSprite().getBoundsInParent().getCenterY());

        if (getState() == State.SPAWNING) {
            return;
        }

        //PauseTransition waitingTransition = new PauseTransition();
        this.followingPath = true;
        double planeWidth = this.getSprite().getBoundsInParent().getWidth();
        double planeHeight = this.getSprite().getBoundsInParent().getHeight();

        randomTransition = new PathTransition();
        Path pathRandom = new Path();

        // Metodo per calcolo random position
        double randomX = getRandomCoordinate(boundWidth, planeWidth);
        double randomY = getRandomCoordinate(boundHeight, planeHeight);
        Point2D moveTo = new Point2D(randomX, randomY);

        //System.out.printf("Random position %d -> x: %f, y: %f\n", this.hashCode(), randomX, randomY);
        // DA DECOMMENTARE
        //Platform.runLater(() -> controllerSeasideFix.drawPoint(randomX, randomY));

        pathRandom.getElements().add(new MoveTo(moveFrom.getX(), moveFrom.getY()));
        pathRandom.getElements().add(new LineTo(randomX, randomY));

        randomTransition.setPath(pathRandom);
        randomTransition.setNode(this.getSprite());
        // Stabilire una velocità standard a seconda della lunghezza del percorso:
        randomPathLength = (moveFrom.distance(moveTo));
        durationRandomTransition = randomPathLength / velocityRandomMovement;
        randomTransition.setDuration(Duration.millis(durationRandomTransition));
        randomTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        setState(State.RANDOM_MOVEMENT);
        randomTransition.play();

        this.getSprite().boundsInParentProperty().addListener((observable, oldValue, newValue) -> {

            try {
                if (newValue.getCenterX() > 0.0 && newValue.getCenterY() > 0.0) {
                    lastPositionX = newValue.getCenterX();
                    lastPositionY = newValue.getCenterY();

                    positionX = lastPositionX;
                    positionY = lastPositionY;

                    //this.getSprite().setX(positionX);
                    //this.getSprite().setY(positionY);

                    //System.out.println("newCenterX: " + positionX);
                    //System.out.println("newCenterY: " + positionY + "\n");
                } else {
                    System.out.println("BUG-Coordianta: " + newValue.getCenterX() + " , " + newValue.getCenterY());
                }
            } catch (StackOverflowError so) {
                System.out.println(so.getMessage());
            }
        });

        randomTransition.setOnFinished(event -> {
            if (getState() == State.RANDOM_MOVEMENT) {
                followingPath = false;
                setState(State.WAITING);
            }
        });
    }

    private double getRandomCoordinate(final double boundCoordinate, final double planeCoordinate) {
        return planeCoordinate * 0.5 + r.nextDouble() * (boundCoordinate - planeCoordinate);
    }

    public void loadPlaneMovementAnimation() {
        if (this.getState() == State.SPAWNING) {
            return;
        }

        if (this.randomTransition.getStatus() == Status.RUNNING) {
            this.randomTransition.stop();
        }

        // L'aereo sta seguendo un percorso...
        this.followingPath = true;
        this.userTransition = new PathTransition();

        double pathLenght = 0;
        final double velocityMovement = 0.03;
        double duration = 0;

        // aggiornare le coordinate da richiamare prima di questo metodo
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

        // Stabilire una velocità standard a seconda della lunghezza del percorso:
        pathLenght = (linesPath.get(0).distance(linesPath.get(linesPath.size() - 1)));
        duration = pathLenght / velocityMovement;
        userTransition.setDuration(Duration.millis(duration));

        setState(State.USER_MOVEMENT);
        userTransition.play();

        userTransition.setOnFinished(event -> {
            linesPath.clear();
            controllerSeaside.clearLinesDrawed();
            controllerSeaside.restoreLinesRemoved();
            this.followingPath = false;
            setState(State.WAITING);
        });


    }

    public PathTransition getSpawnTransition() {
        return this.spawnTransition;
    }

    public ExplosionAnimation getExplosionAnimation() {
        return this.explosionAnimation;
    }

    public boolean isFollowingPath() {
        return this.followingPath;
    }

    public void stopPlaneMovementAnimation() {
        if (this.userTransition != null) {
            this.userTransition.stop();
        }
    }

    public String getStatusMovementAnimation() {
        try {
            if (this.userTransition == null) {
                return ("WAITING");
            } else if (this.userTransition.getStatus() == Status.STOPPED) {
                return ("WAITING");
            }
        } catch (Exception e) { }
        return this.userTransition.getStatus().toString();
    }

    public PathTransition getPlaneMovementAnimation() {
        return this.userTransition;
    }

    public void startRandomTransition() {
        this.randomTransition.play();
    }

    public void stopRandomTransition() {
        if (this.randomTransition != null) {
            this.randomTransition.stop();
        }
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

            if (linesPath.get(k).getX() > 0 && linesPath.get(k).getY() > 0) {
                if (k == 0) {
                    path.getElements().add(new MoveTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
                } else {
                    path.getElements().add(new LineTo(linesPath.get(k).getX(), linesPath.get(k).getY()));
                }
            }
        }
    }

    public void connectToController(final Seaside controllerSeaside) {
        this.controllerSeaside = controllerSeaside;
    }

    // Gestisce il cambio di immagine da Plane --> PlaneSelected quando si seleziona il Plane desiderato
    public void setOnClick() {
        this.getSprite().setOnMousePressed(event -> {
            setSpritePlane("images/map_components/airplaneSelected.png");
            isPlaneSelectedForBeenMoved = true;

            if (controllerSeaside.isMoreThanOneSelected()) {
                System.out.println("image Plane cambiata");
                controllerSeaside.clearPlaneSelectedForBeenMoved();
                isPlaneSelectedForBeenMoved = true;
            }

        });

        this.getSprite().setOnMouseReleased(event -> {
            this.getSprite().setImage(new Image("images/map_components/airplane.png"));
        });
    }

    // Aggiungo le coordinate campionate nel Plane
    public synchronized void setPlaneLinesPath(final List<Point2D> linesPath) {
        List<Point2D> linesPathClear;

        for (Point2D lines:linesPath) {
            System.out.println("coordinate passate prima: " + lines.getX() + " , " + lines.getY());
        }

        if (this.getIsPlaneSelectedForBeenMoved()) {
            this.linesPath.clear();

            linesPathClear = removeDuplicateInLinesPath(linesPath);

           for (Point2D linesClear:linesPathClear) {
               System.out.println("coordinate passate dopo: " + linesClear.getX() + " , " + linesClear.getY());
               this.linesPath.add(new Point2D(linesClear.getX(), linesClear.getY()));
            }
        }
    }

    public List<Point2D> removeDuplicateInLinesPath(final List<Point2D> linesPath) {
        List<Point2D> linesPathCopy = linesPath;
        double xCopy;
        double yCopy;

        for (int k = 0; k < linesPath.size(); k++) {
            xCopy = linesPath.get(k).getX();
            yCopy = linesPath.get(k).getY();

            for (int j = 0; j < linesPath.size(); j++) {
                if (j != k && linesPath.get(j).getX() == xCopy && linesPath.get(j).getY() == yCopy) {
                    linesPathCopy.remove(j);
                }
            }
        }
        return linesPathCopy;
    }


    /*
     * public void resetPlaneLinesPath() { this.linesPath.clear(); }
     */

    /*
     * public void resetPlaneLinesPath(final int idPlane) { resetPlaneLinesPath(); }
     */


    public List<Point2D> getPlaneLinesPath() {
        //System.out.println("getPlaneLinesPath in Plane = " + this.linesPath);
        return this.linesPath;
    }

    public List<Point2D> getPlaneLinesPathToRemove() {
        return this.linesPathToRemove;
    }

    public void setPlaneLinesPathToRemove(final List<Point2D> linesPathToRemove) {
        for (Point2D pointToRemove : linesPathToRemove) {
            //System.out.println("linesPathToRemove: " + pointToRemove.getX() + " , " + pointToRemove.getY());
        }

        this.linesPathToRemove = linesPathToRemove;
    }

    /**
     * @return the ImageView of the Plane object.
     */
    public ImageView getSprite() {
        return this.spritePlane.getSprite();
    }

    // Da adattare a ImageView
    public void setSpritePlane(final String newUrlImage) {
        //this.spritePlane.getImageSpriteResized().setImageSprite(newUrlImage);
        this.spritePlane.getSprite().setImage(new Image(newUrlImage));
    }

    public ScaleTransition getLandingAnimation() {
        return landingAnimation.getLandingAnimation();
    }

    public void setIsPlaneSelectedForBeenMoved(final boolean isPlaneSelectedForBeenMoved) {
        this.isPlaneSelectedForBeenMoved = isPlaneSelectedForBeenMoved;
    }

    public boolean getIsPlaneSelectedForBeenMoved() {
        return this.isPlaneSelectedForBeenMoved;
    }

    public synchronized State getState() {
        return obsState.getState();
    }

    public synchronized void setState(final State state) {
        this.obsState.setState(state);
    }

    public void removedObservableStateListener() {
        this.obsState.removeListener();
    }

    public boolean isLanded() {
        return obsState.getState() == State.LANDED;
    }

}
