package alt.sim.controller.seaside;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import alt.sim.Main;
import alt.sim.controller.game.GameController;
import alt.sim.controller.map.MapController;
import alt.sim.controller.user.records.UserRecordsController;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.animation.ExplosionAnimation;
import alt.sim.model.engine.GameEngineImpl;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.PlaneMovement;
import alt.sim.model.plane.State;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.model.sprite.SpriteType;
import alt.sim.view.common.WindowView;
import alt.sim.view.pages.Page;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SeasideController {

    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imgViewHelicopterLandingArea;
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField score;
    @FXML
    private Rectangle landingBoxLeft;
    @FXML
    private Rectangle landingBoxRight;
    @FXML
    private Canvas canvas;

    private static final int FADE_DURATION_INDICATOR = 1000;
    private static final  int SCREEN_MIN_WIDTH = 1080;
    private static final  int SCREEN_MIN_HEIGHT = 720;

    // Timer: at the end of count down a new Plane gets spawned
    private Timeline spawnCountDown;

    private ImageView indicatorTop;
    private ImageView indicatorLeft;
    private ImageView indicatorRight;
    private ImageView indicatorBottom;

    private FadeTransition fadeTop;
    private FadeTransition fadeLeft;
    private FadeTransition fadeRight;
    private FadeTransition fadeBottom;

    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;
    private List<Point2D> planeCoordinates;

    private GraphicsContext gc;
    private GameEngineImpl engine;
    private Game gameSession;

    @FXML
    public void initialize() {
        gameSession = new Game();

        stripLeft = new BasicAirStrip(SpriteType.AIRSTRIP.getURLImage());
        stripRight = new BasicAirStrip(SpriteType.AIRSTRIP.getURLImage());
        engine = GameEngineImpl.getInstance(this, gameSession);

        gc = canvas.getGraphicsContext2D();
        planeCoordinates = new ArrayList<>();

        this.indicatorTop = new ImageView(new Image(SpriteType.INDICATOR.getURLImage()));
        this.indicatorLeft = new ImageView(new Image(SpriteType.INDICATOR.getURLImage()));
        this.indicatorRight = new ImageView(new Image(SpriteType.INDICATOR.getURLImage()));
        this.indicatorBottom = new ImageView(new Image(SpriteType.INDICATOR.getURLImage()));

        this.fadeTop = new FadeTransition();
        this.fadeLeft = new FadeTransition();
        this.fadeRight = new FadeTransition();
        this.fadeBottom = new FadeTransition();

        List<ImageView> indicatorList = List.of(indicatorTop, indicatorRight, indicatorBottom, indicatorLeft);
        indicatorList.forEach(indicator -> indicator.setVisible(false));
        pane.getChildren().addAll(indicatorList);

        System.out.println("Main: " + Main.getStage().getWidth() + " , " + Main.getStage().getHeight());

        // At the beginning of the game 1 plane gets spawned each time
        // till 500 points are reached, than this variable will be incremented
        // till reaching 4 planes
        gameSession.setNumberPlanesToSpawnEachTime(1);

        imgViewHelicopterLandingArea.setX(
                (pane.getBoundsInLocal().getWidth() / 2) - imgViewHelicopterLandingArea.getFitWidth()
                );
        imgViewHelicopterLandingArea.setY(
                (pane.getBoundsInLocal().getHeight() / 2) - imgViewHelicopterLandingArea.getFitHeight() / 2
                );

        ((BasicAirStrip) stripLeft).setBox(landingBoxLeft);
        ((BasicAirStrip) stripRight).setBox(landingBoxRight);

        this.spawnCountDown = new Timeline(new KeyFrame(Duration.seconds(Game.getSpawnDelay()), this::handle));

        spawnCountDown.setCycleCount(Animation.INDEFINITE);
        spawnCountDown.play();
        name.setText(MapController.getName());
        gameSession.setInGame(true);

        class ThreadEngine implements Runnable {

            @Override
            public void run() {
                try {
                    engine.mainLoop();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        Thread t = new Thread(new ThreadEngine());
        t.start();
    }

    /**
     * when the Mouse is dragged, the planeCoordinates save the coordinates of Mouse.
     * the GraphicContext gc draw the lines into the Canvas.
     */
    @FXML
    void handlerMouseDragged(final MouseEvent event) {
        for (Plane planeSelected : gameSession.getPlanes()) {
            if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT && planeSelected.isPlaneSelectedForBeenMoved()) {
                planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.BLUE);
                gc.stroke();
            }
        }
    }

    @FXML
    void handlerMouseReleased() {
        Point2D pathStartingPoint;
        double distanceFromPlane;

        for (Plane planeSelected : gameSession.getPlanes()) {

            // Checks if user has drawn a path with a minimum amount of points
            if (planeSelected.isPlaneSelectedForBeenMoved()
                    && planeCoordinates.size() > PlaneMovement.MIN_COORDINATES_LENGTH) {

                pathStartingPoint = new Point2D(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY());
                distanceFromPlane = pathStartingPoint
                        .distance(new Point2D(planeSelected.getSprite().getBoundsInParent().getCenterX(),
                                planeSelected.getSprite().getBoundsInParent().getCenterY()));

                // Animation starts only if the user has drawn a path near the Plane
                if (distanceFromPlane <= PlaneMovement.MAX_DISTANCE_DRAWINGPATH_VALUE
                        && planeSelected.getState() != State.SPAWNING) {

                    // When the mouse is released if Plane was already following a path,
                    // it has to stop and follow the new one
                    if (planeSelected.getPlaneMovementAnimation() != null) {
                        planeSelected.stopPlaneMovementAnimation();
                    }

                    planeSelected.setPlaneLinesPath(planeCoordinates);
                    clearLinesDrawed();
                    restoreLinesRemoved();

                    // Once coordinates are loaded and running animations are stopped,
                    // plane movement animation is loaded
                    planeSelected.loadPlaneMovementAnimation();
                    clearPlaneSelectedForBeenMoved();

                } else {
                    clearMap();
                }
            } else {
                clearMap();
            }
        }

        // Saved-coordinates list gets cleared
        planeCoordinates.clear();
    }


    /**
     * @param numberPlaneSpawn passed the number of Plane to spawn, there are created and spawned calling the Plane method playSpawnTransition().
     */
    public synchronized void spawnPlane(final int numberPlaneSpawn) {
        if (gameSession.isInGame()) {

            List<SpawnLocation> spawnLocation = new ArrayList<>(Arrays.asList(SpawnLocation.values()));

            for (int i = 0; i < numberPlaneSpawn; i++) {
                if (gameSession.getPlanes().size() < Game.getMaxPlaneToSpawn()) {
                    int locationIndex = ThreadLocalRandom.current().nextInt(spawnLocation.size());
                    Plane newPlane = new Plane(SpriteType.AIRPLANE.getURLImage());
                    newPlane.connectToController(this);
                    newPlane.playSpawnAnimation(spawnLocation.get(locationIndex));
                    loadIndicatorAnimation(spawnLocation.get(locationIndex));
                    spawnLocation.remove(locationIndex);
                    gameSession.addPlane(newPlane);
                    pane.getChildren().add(newPlane.getSprite());
                }
            }
        }
    }

    /**
     * @param side selected a side where collocated the indicator, there image are created and setted into the Map with FadeTransition animation.
     */
    public void loadIndicatorAnimation(final SpawnLocation side) {
        final int delta = 50;

        final double width = pane.getWidth();
        final double height = pane.getHeight();
        final double halfWidth = width / 2.0;
        final double halfHeight = height / 2.0;

        switch (side) {
        case TOP:
            indicatorTop.setX(halfWidth - (indicatorTop.getBoundsInLocal().getWidth() / 2));
            indicatorTop.setY(delta);

            this.setFadeTransition(fadeTop, indicatorTop);
            indicatorTop.setVisible(true);
            break;

        case RIGHT:
            indicatorRight.setX(width - delta);
            indicatorRight.setY(halfHeight - (indicatorRight.getBoundsInLocal().getHeight() / 2));

            this.setFadeTransition(fadeRight, indicatorRight);
            indicatorRight.setVisible(true);
            break;

        case BOTTOM:
            indicatorBottom.setX(halfWidth - (indicatorBottom.getBoundsInLocal().getWidth() / 2));
            indicatorBottom.setY(height - delta);

            this.setFadeTransition(fadeBottom, indicatorBottom);
            indicatorBottom.setVisible(true);
            break;

        case LEFT:
            indicatorLeft.setX(delta);
            indicatorLeft.setY(halfHeight - (indicatorLeft.getBoundsInLocal().getHeight() / 2));

            this.setFadeTransition(fadeLeft, indicatorLeft);
            indicatorLeft.setVisible(true);
            break;

        default:
            break;
        }
    }

    public void terminateGame() {
        gameSession.setInGame(false);
        gameSession.setNumberPlanesToSpawnEachTime(0);

        // Stops all animations
        GameController.stop();

        Platform.runLater(() -> {
            try {
                UserRecordsController.updateScore(name.getText(), getIntScore());
                WindowView.showDialog(Page.GAMEOVER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @return checked if there are more than one Plane selected for choose after the last Plane selected between the two.
     */
    public boolean isMoreThanOneSelected() {
        int planeBeenSelected = 0;

        for (Plane planeSelected:gameSession.getPlanes()) {
            if (planeSelected.isPlaneSelectedForBeenMoved()) {
                planeBeenSelected++;
            }
        }

        return planeBeenSelected >= 2;
    }

    public void clearPlaneSelectedForBeenMoved() {
        for (Plane planeSelected : gameSession.getPlanes()) {
            planeSelected.setIsPlaneSelectedForBeenMoved(false);
        }
    }

    public void clearLinesDrawed() {
        final double dimensionRectangleCleanerWidth = Main.getStage().getWidth();
        final double dimensionRectangleCleanerHeight = Main.getStage().getHeight();

        gc.clearRect(0, 0, dimensionRectangleCleanerWidth, dimensionRectangleCleanerHeight);
    }

    public void clearMap() {
        gc.beginPath();
        clearLinesDrawed();
        restoreLinesRemoved();
    }

    public void restoreLinesRemoved() {
        for (Plane planeSelected : gameSession.getPlanes()) {
            try {
                if (planeSelected.getPlaneLinesPath().size() > 0) {
                    gc.moveTo(
                            planeSelected.getPlaneLinesPath().get(0).getX(),
                            planeSelected.getPlaneLinesPath().get(0).getY()
                            );

                    for (int k = 1; k < planeSelected.getPlaneLinesPath().size(); k++) {
                        gc.lineTo(
                                planeSelected.getPlaneLinesPath().get(k).getX(),
                                planeSelected.getPlaneLinesPath().get(k).getY()
                                );
                    }

                    gc.setStroke(Color.BLUE);
                    gc.stroke();
                    gc.beginPath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onPauseClick() throws IOException {
        GameController.pause();
        WindowView.showDialog(Page.PAUSE);
    }

    /**
     * @param explosionAnimation the Animation of the Explosion.
     * @param planeCollided the Plane that collided and need the ExplosionAnimation.
     */
    public void startExplosionToPane(final ExplosionAnimation explosionAnimation, final Plane planeCollided) {
        Platform.runLater(() -> {
            pane.getChildren().add(explosionAnimation.getImgExplosion());
            explosionAnimation.getImgExplosion().setX(planeCollided.getSprite().getBoundsInParent().getCenterX());
            explosionAnimation.getImgExplosion().setY(planeCollided.getSprite().getBoundsInParent().getCenterY());
            explosionAnimation.start();
        });
    }

    /**
     * @param planes removes all the Planes in Game after the Game is over.
     */
    public void removePlanes(final Collection<? extends Plane> planes) {
        final List<ImageView> imageViews = planes.stream()
                .map(Plane::getSprite)
                .collect(Collectors.toList());
        Platform.runLater(() -> pane.getChildren().removeAll(imageViews));

        gameSession.removePlanes();
    }

    /**
     * @param plane removes a single Planes in Game after Plane is landed.
     */
    public synchronized void removePlane(final Plane plane) {
        Platform.runLater(() -> {
            System.out.println("Plane landing...");
            this.gameSession.getPlanes().removeIf(obj -> obj.hashCode() == plane.hashCode());

            Iterator<Plane> iter = this.gameSession.getPlanes().iterator();
            while (iter.hasNext()) {
                if (iter.next().hashCode() == plane.hashCode()) {
                    System.out.println("Plane remove: " + iter.hashCode());
                    iter.remove();
                }
            }

            pane.getChildren().remove(plane.getSprite());
        });
    }

    /**
     * @return the Left AirStrip component.
     */
    public AbstractAirStrip getStripLeft() {
        return this.stripLeft;
    }

    /**
     * @return the Right AirStrip component.
     */
    public AbstractAirStrip getStripRight() {
        return this.stripRight;
    }

    /**
     * @return the AnchorPane component.
     */
    public AnchorPane getPane() {
        return this.pane;
    }

    /**
     * @param score set the Game Score into the Label.
     */
    public void addScore(final int score) {
        Platform.runLater(() -> this.score.setText(String.valueOf(Integer.parseInt(this.score.getText()) + score)));
    }

    /**
     * @return Game Score of the Math.
     */
    public int getIntScore() {
        return Integer.parseInt(this.score.getText());
    }

    /**
     * @param fade the FadeAnimation that need to be setted.
     * @param indicator Image of indicator where apply the FadeTransition.
     */
    public void setFadeTransition(final FadeTransition fade, final ImageView indicator) {
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setDuration(Duration.millis(FADE_DURATION_INDICATOR));
        fade.setCycleCount(3);
        fade.setNode(indicator);

        fade.play();
    }

    /**
     * @return the List of FadeTransition used depending the Side where the Plane will spawn.
     */
    public List<FadeTransition> getFadeTransition() {
        return List.of(this.fadeTop, this.fadeLeft, this.fadeRight, this.fadeBottom);
    }

    /**
     * @return the countdown of Spawn between a Plane and another.
     */
    public Timeline getSpawnCountDown() {
        return this.spawnCountDown;
    }

    public static int getScreenMinWidth() {
        return SCREEN_MIN_WIDTH;
    }

    public static int getScreenMinHeight() {
        return SCREEN_MIN_HEIGHT;
    }

    private void handle(final ActionEvent cycle) {
        spawnPlane(gameSession.getNumberPlanesToSpawnEachTime());
    }
}
