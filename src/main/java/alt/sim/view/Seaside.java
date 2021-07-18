package alt.sim.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import alt.sim.controller.MapController;
import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.model.ExplosionAnimation;
import alt.sim.model.PlaneMovement;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.model.user.records.UserRecordsImpl;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Seaside {
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imgViewHelicopterLandingArea;
    @FXML
    private ImageView imgViewPlaneLandingArea;
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField score = new TextField();
    @FXML
    private Rectangle landingBoxLeft;
    @FXML
    private Rectangle landingBoxRight;
    @FXML
    private Canvas canvas;

    // Timer for Plane spawn;
    private Timer spawnTimer;
    private TimerTask spawnTask;

    private List<Plane> planes;
    private Plane plane;
    private Plane plane2;
    private Plane plane3;
    private Plane plane4;
    private AbstractAirStrip strip;
    private List<Point2D> planeCoordinates;
    private GraphicsContext gc;
    private GameEngineAreaTest engine;

    // Sezione EventHandler<Mouse>
    private EventHandler<MouseEvent> handlerMouseReleased;
    private EventHandler<MouseEvent> handlerMouseDragged;

    private static final Stage POPUP_STAGE = new Stage(StageStyle.UNDECORATED);
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        score.setText(String.valueOf(Integer.parseInt(score.getText()) + 10));
    }));

    public void playSpawnTimer() {
        this.spawnTimer = new Timer();
        PathTransition spawnTransition = new PathTransition();
        Path pathSpawn = new Path();

        Platform.runLater(() -> {

            Plane planeSpawned = new Plane("images/map_components/airplane.png");
            //Adding planeSpawned to planes
            planes.add(planeSpawned);
            planeSpawned.connectToController(this);
            planeSpawned.setState(State.SPAWNING);

            engine.setPlanes(planes);

            planeSpawned.getImagePlane().setX(1000);
            planeSpawned.getImagePlane().setY(680);

            // Rimpicciolimento planeSpawned
            planeSpawned.getImagePlane().setFitWidth(64);
            planeSpawned.getImagePlane().setFitHeight(64);

            // Inserimento coordinate PathSpawn from LEFT
            pathSpawn.getElements().add(new MoveTo(-80, 360));
            pathSpawn.getElements().add(new LineTo(60, 360));

            pane.getChildren().add(planeSpawned.getImagePlane());

            spawnTransition.setPath(pathSpawn);
            spawnTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            spawnTransition.setNode(planeSpawned.getImagePlane());
            spawnTransition.setDuration(Duration.millis(3000));
            spawnTransition.play();
            spawnTransition.setOnFinished(event -> planeSpawned.setState(State.WAITING));
        });
    }

    @FXML
    public void initialize() {
        strip = new BasicAirStrip("images/map_components/airstrip.png");
        Game newGame = new Game();
        engine = new GameEngineAreaTest(this);

        engine.setLandingBoxLeft(landingBoxLeft);
        engine.setLandingBoxRight(landingBoxRight);

        newGame.startGame();
        gc = canvas.getGraphicsContext2D();
        planeCoordinates = new ArrayList<Point2D>();

        //imgViewPlaneLandingArea.setX((pane.getBoundsInLocal().getWidth() / 2) - imgViewPlaneLandingArea.getFitWidth());
        //imgViewPlaneLandingArea.setY((pane.getBoundsInLocal().getHeight() / 2) - imgViewPlaneLandingArea.getFitHeight() / 2);

        // Spostando i 2 landingBoxArea
        //landingBoxLeft.setX(imgViewPlaneLandingArea.getX());
        //landingBoxLeft.setY(imgViewPlaneLandingArea.getY());
        //landingBoxRight.setX(imgViewPlaneLandingArea.getX() + 2.5);
        //landingBoxRight.setY(imgViewPlaneLandingArea.getY() + 20);

        imgViewHelicopterLandingArea.setX((pane.getBoundsInLocal().getWidth() / 2) - imgViewHelicopterLandingArea.getFitWidth());
        imgViewHelicopterLandingArea.setY((pane.getBoundsInLocal().getHeight() / 2) - imgViewHelicopterLandingArea.getFitHeight() / 2);

        planes = new ArrayList<>();
        plane = new Plane("images/map_components/airplane.png");
        plane2 = new Plane("images/map_components/airplane.png");
        plane3 = new Plane("images/map_components/airplane.png");
        plane4 = new Plane("images/map_components/airplane.png");
        strip.setAirStripImage(imgViewPlaneLandingArea);
        ((BasicAirStrip) strip).setBoxLeft(landingBoxLeft);
        ((BasicAirStrip) strip).setBoxRight(landingBoxRight);

        plane.getImagePlane().setFitWidth(64);
        plane.getImagePlane().setFitHeight(64);
        plane2.getImagePlane().setFitWidth(64);
        plane2.getImagePlane().setFitHeight(64);
        plane3.getImagePlane().setFitWidth(64);
        plane3.getImagePlane().setFitHeight(64);
        plane4.getImagePlane().setFitWidth(64);
        plane4.getImagePlane().setFitHeight(64);

        planes.add(plane);
        planes.add(plane2);
        planes.add(plane3);
        planes.add(plane4);

        plane2.getImagePlane().setX(900);
        plane2.getImagePlane().setY(500);
        plane3.getImagePlane().setX(1000);
        plane3.getImagePlane().setY(100);
        plane4.getImagePlane().setX(600);
        plane4.getImagePlane().setY(600);

        plane.connectToController(this);
        plane2.connectToController(this);
        plane3.connectToController(this);
        plane4.connectToController(this);

        //engine.setGraphicContext(gc);
        engine.setPlanes(planes);

        this.handlerMouseDragged = event -> {
            if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.BLUE);
                gc.stroke();
            }
        };

        this.handlerMouseReleased = event -> {
            Point2D puntoInizioPercorso;
            double distanzaDalPlane = 0;

            for (Plane planeSelected:planes) {
                // Controllo che l'utente disegni un percorso con un minimo di punti
                if (planeSelected.getIsPlaneSelectedForBeenMoved() && planeCoordinates.size() > PlaneMovement.MIN_COORDINATES_LENGTH) {
                    puntoInizioPercorso = new Point2D(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY());
                    distanzaDalPlane = puntoInizioPercorso.distance(new Point2D(planeSelected.getImagePlane().getBoundsInParent().getCenterX(), planeSelected.getImagePlane().getBoundsInParent().getCenterY()));

                    // L'animazione parte solo se l'utente ha disegnato una rotta vicino al Plane
                    if (distanzaDalPlane <= PlaneMovement.MAX_DISTANCE_DRAWINGPATH_VALUE) {

                        // Quando viene rilasciato il Mouse se il Plane seguiva già un percoso,
                        // deve bloccarsi e seguire il percorso Nuovo
                        if (planeSelected.getPlaneMovementAnimation() != null) {
                            planeSelected.stopPlaneMovementAnimation();
                        }

                        planeSelected.setPlaneLinesPath(planeCoordinates);
                        clearLinesDrawed();
                        restoreLinesRemoved();

                        // Una volta caricate le coordinate e stoppate le animazioni in esecuzione
                        // viene fatta partire quella del Plane
                        planeSelected.loadPlaneMovementAnimation();
                        planeSelected.startPlaneMovementAnimation();
                    } else {
                        clearMap();
                    }
                } else {
                    clearMap();
                }
            }
            // La lista di Coordinate salvate vengono ripulite
            planeCoordinates.clear();
        };

        // Calcolare posizioni dove far spawnare i Plane:
        //...

        name.setText(MapController.getName());
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);

        pane.getChildren().addAll(plane.getImagePlane(), plane2.getImagePlane());
        pane.getChildren().addAll(plane3.getImagePlane(), plane4.getImagePlane());

        playSpawnTimer();

        /*
            playSpawnTimer();
            playSpawnTimer();
            playSpawnTimer();
            playSpawnTimer();
            playSpawnTimer();
            playSpawnTimer();
         */

        // Avvio del GameLoop
        class ThreadEngine implements Runnable {

            @Override
            public void run() {
                engine.mainLoop();
            }
        }

        Thread t = new Thread(new ThreadEngine());
        t.start();
    }

    public void terminateGame() {
        // Blocco del GameLoop
        engine.setEngineStart(false);

        // Terminazione di tutte le animazioni del Plane in corso
        for (Plane planeSelected:planes) {
            if (planeSelected.getPlaneMovementAnimation() != null) {
                planeSelected.getPlaneMovementAnimation().stop();
            }

            if (planeSelected.getLandingAnimation() != null) {
                planeSelected.getLandingAnimation().stop();
            }

            if (planeSelected.getRandomTransition() != null) {
                planeSelected.getRandomTransition().stop();
            }
        }

        // Disattivazione EventHandlerMouse, NON FUNZIONANO
        canvas.removeEventFilter(MouseEvent.ANY, handlerMouseDragged);
        canvas.removeEventFilter(MouseEvent.ANY, handlerMouseReleased);
        pane.setDisable(true);

        Platform.runLater(() -> {
            PageLoader.loadPage(Page.GAMEOVER);
        });
    }

    public boolean isMoreThanOneSelected() {
        int planeBeenSelected = 0;

        for (Plane planeSelected:planes) {
            if (planeSelected.getIsPlaneSelectedForBeenMoved()) {
                planeBeenSelected++;
            }
        }

        return planeBeenSelected >= 2;
    }

    public void clearPlaneSelectedForBeenMoved() {
        for (Plane planeSelected:planes) {
            planeSelected.setIsPlaneSelectedForBeenMoved(false);
        }
    }

    public void clearLinesDrawed() {
        final double dimensionRectangleCleanerWidth = 2000;
        final double dimensionRectangleCleanerHeight = 2000;

        gc.clearRect(0, 0, dimensionRectangleCleanerWidth, dimensionRectangleCleanerHeight);
    }

    public void clearMap() {
        gc.beginPath();
        clearLinesDrawed();
        restoreLinesRemoved();
    }

    public void restoreLinesRemoved() {
        for (Plane planeSelected:planes) {
            try {
                if (planeSelected.getPlaneLinesPath().size() > 0) {
                    gc.moveTo(planeSelected.getPlaneLinesPath().get(0).getX(), planeSelected.getPlaneLinesPath().get(0).getY());

                    for (int k = 1; k < planeSelected.getPlaneLinesPath().size(); k++) {
                        gc.lineTo(planeSelected.getPlaneLinesPath().get(k).getX(), planeSelected.getPlaneLinesPath().get(k).getY());
                    }

                    gc.setStroke(Color.BLUE);
                    gc.stroke();
                    gc.beginPath();
                }
            } catch (Exception e) { }
        }
    }

    @FXML
    public void onPauseClick() throws IOException {
        timeline.pause();

        // TODO: updates user score on pause click, TO change cause takes from Model
        new UserRecordsImpl().updateScore(name.getText(), Integer.parseInt(score.getText()));

        CommonView.onPauseClick();
        timeline.play();
    }

    public void startExplosionToPane(final ExplosionAnimation testExplosion, final Plane planeCollided) {
        Platform.runLater(() -> {
                pane.getChildren().add(testExplosion.getImgExplosion());
                testExplosion.getImgExplosion().setX(planeCollided.getImagePlane().getBoundsInParent().getCenterX());
                testExplosion.getImgExplosion().setY(planeCollided.getImagePlane().getBoundsInParent().getCenterY());
                testExplosion.startExplosion();
        });
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void removePlanes(final Collection<? extends Plane> planes) {
        Platform.runLater(() -> pane.getChildren().removeAll(planes.stream()
                .map(Plane::getImagePlane)
                .collect(Collectors.toList())));
    }

    public AbstractAirStrip getStrip() {
        return this.strip;
    }
}
