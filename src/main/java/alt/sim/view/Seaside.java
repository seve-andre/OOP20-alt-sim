package alt.sim.view;

import alt.sim.controller.MapController;
import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.controller.user.records.UserRecordsController;
import alt.sim.model.ExplosionAnimation;
import alt.sim.model.PlaneMovement;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.model.spawn.SpawnModel;
import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
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
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Seaside {

    @FXML
    private AnchorPane pane = new AnchorPane();
    @FXML
    private ImageView imgViewHelicopterLandingArea;
    @FXML
    private ImageView imgViewPlaneLandingArea;
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

    private List<PathTransition> pathTransitionList = new ArrayList<>();
    private List<SpawnLocation> spawnLocationList = new ArrayList<>();

    private List<Plane> planes;
    private AbstractAirStrip strip;
    private List<Point2D> planeCoordinates;
    private GraphicsContext gc;
    private GameEngineAreaTest engine;

    // Sezione EventHandler<Mouse>
    private EventHandler<MouseEvent> handlerMouseReleased;
    private EventHandler<MouseEvent> handlerMouseDragged;

    public void playGame() {
        this.planes = SpawnModel.generatePlanes();

        List<ImageView> planeImages = this.planes.stream()
                .map(Plane::getImagePlane)
                .collect(Collectors.toUnmodifiableList());


        Platform.runLater(() -> {

            pane.getChildren().addAll(planeImages);
            pane.getChildren().addAll(SpawnModel.generateIndicators());
            for (Plane plane : this.planes) {
                plane.connectToController(this);

                while (spawnLocationList.size() != 4) {
                    SpawnLocation random = SpawnLocation.getRandomSpawnLocation();
                    if (!spawnLocationList.contains(random)) {
                        spawnLocationList.add(random);
                        this.pathTransitionList.add(SpawnModel.spawn(plane, random));
                        break;
                    }
                }
            }

            engine.setPlanes(planes);
            engine.setEngineStart(true);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(this.pathTransitionList);
            parallelTransition.play();

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

            /*PauseTransition pt = new PauseTransition(Duration.seconds(5));
            //pt.setCycleCount(Timeline.INDEFINITE);
            SequentialTransition sq = new SequentialTransition(pl,pt);
            sq.setCycleCount(Timeline.INDEFINITE);
            sq.play();*/

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
        planeCoordinates = new ArrayList<>();

        // Spostando i 2 landingBoxArea
        //landingBoxLeft.setX(imgViewPlaneLandingArea.getX());
        //landingBoxLeft.setY(imgViewPlaneLandingArea.getY());
        //landingBoxRight.setX(imgViewPlaneLandingArea.getX() + 2.5);
        //landingBoxRight.setY(imgViewPlaneLandingArea.getY() + 20);

        imgViewHelicopterLandingArea.setX(
                (pane.getBoundsInLocal().getWidth() / 2) - imgViewHelicopterLandingArea.getFitWidth()
        );
        imgViewHelicopterLandingArea.setY(
                (pane.getBoundsInLocal().getHeight() / 2) - imgViewHelicopterLandingArea.getFitHeight() / 2
        );

        strip.setAirStripImage(imgViewPlaneLandingArea);
        ((BasicAirStrip) strip).setBoxLeft(landingBoxLeft);
        ((BasicAirStrip) strip).setBoxRight(landingBoxRight);

        this.handlerMouseDragged = event -> {
            if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.BLUE);
                gc.stroke();
            }
        };

        this.handlerMouseReleased = event -> {
            Point2D startingPoint;
            double distanceFromPlane;

            for (Plane planeSelected : planes) {
                // Controllo che l'utente disegni un percorso con un minimo di punti
                if (planeSelected.getIsPlaneSelectedForBeenMoved()
                        && planeCoordinates.size() > PlaneMovement.MIN_COORDINATES_LENGTH) {
                    startingPoint = new Point2D(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY());
                    distanceFromPlane = startingPoint.distance(
                            new Point2D(
                                    planeSelected.getImagePlane().getBoundsInParent().getCenterX(),
                                    planeSelected.getImagePlane().getBoundsInParent().getCenterY()
                                    )
                    );

                    // L'animazione parte solo se l'utente ha disegnato una rotta vicino al Plane
                    if (distanceFromPlane <= PlaneMovement.MAX_DISTANCE_DRAWINGPATH_VALUE) {

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

        name.setText(MapController.getName());

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);

        playGame();

    }

    public void terminateGame() {
        // Blocco del GameLoop
        engine.setEngineStart(false);

        // Terminazione di tutte le animazioni del Plane in corso
        for (Plane planeSelected : planes) {
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
            try {
                UserRecordsController.updateScore(name.getText(), getIntScore());
                CommonView.showDialog(Page.GAMEOVER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isMoreThanOneSelected() {
        int planeBeenSelected = 0;

        for (Plane planeSelected : planes) {
            if (planeSelected.getIsPlaneSelectedForBeenMoved()) {
                planeBeenSelected++;
            }
        }

        return planeBeenSelected >= 2;
    }

    public void clearPlaneSelectedForBeenMoved() {
        for (Plane planeSelected : planes) {
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
        for (Plane planeSelected : planes) {
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
            }
        }
    }

    @FXML
    public void onPauseClick() throws IOException {
        // UserRecordsController.updateScore(name.getText(), getIntScore());
        CommonView.showDialog(Page.PAUSE);
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

    public AnchorPane getPane() {
        return this.pane;
    }

    public void setScore(final int score) {
        this.score.setText(String.valueOf(Integer.parseInt(this.score.getText()) + score));
    }

    public int getIntScore() {
        return Integer.parseInt(this.score.getText());
    }
}
