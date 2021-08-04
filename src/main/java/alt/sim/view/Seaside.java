package alt.sim.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import alt.sim.controller.MapController;
import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.controller.game.GameController;
import alt.sim.controller.user.records.UserRecordsController;
import alt.sim.model.ExplosionAnimation;
import alt.sim.model.PlaneMovement;
import alt.sim.model.SpriteType;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.model.spawn.SpawnModel;
import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

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

    @FXML
    void handlerMouseDragged(MouseEvent event) {
        if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
            planeCoordinates.add(new Point2D(event.getX(), event.getY()));
            gc.lineTo(event.getX(), event.getY());
            gc.setStroke(Color.BLUE);
            gc.stroke();
        }
    }

    @FXML
    void handlerMouseReleased(MouseEvent event) {
        Point2D puntoInizioPercorso;
        double distanzaDalPlane = 0;

        for (Plane planeSelected : planes) {
            // Controllo che l'utente disegni un percorso con un minimo di punti
            if (planeSelected.getIsPlaneSelectedForBeenMoved()
                    && planeCoordinates.size() > PlaneMovement.MIN_COORDINATES_LENGTH) {
                puntoInizioPercorso = new Point2D(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY());
                distanzaDalPlane = puntoInizioPercorso
                        .distance(new Point2D(planeSelected.getSprite().getBoundsInParent().getCenterX(),
                                planeSelected.getSprite().getBoundsInParent().getCenterY()));

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
                } else {
                    clearMap();
                }
            } else {
                clearMap();
            }
        }

        // La lista di Coordinate salvate vengono ripulite
        planeCoordinates.clear();
    }

    /**
     * Campo contenente le informazioni globali sullo Schermo della Partita.
     */
    public static final Screen SCREEN_GAME = Screen.getPrimary();

    /**
     * Informazioni globali sui Bound dello Schermo.
     */
    public static final Rectangle2D SCREEN_BOUND = SCREEN_GAME.getVisualBounds();

    // Section Spawn Plane
    private static final int MAX_PLANE_TO_SPAWN = 1;

    private int numberPlanesToSpawnEachTime;
    //------------------------------------------------------------------------
    // Timer che al termine del count, fa spawnare un Plane
    private Timeline spawnCountDown;

    //------------------------------------------------------------------------
    // Sezione Indicator
    private ImageView indicatorTop;
    private ImageView indicatorLeft;
    private ImageView indicatorRight;
    private ImageView indicatorBottom;

    private FadeTransition fadeTop;
    private FadeTransition fadeLeft;
    private FadeTransition fadeRight;
    private FadeTransition fadeBottom;

    private List<PathTransition> pathTransitionList = new ArrayList<>();
    private List<SpawnLocation> spawnLocationList = new ArrayList<>();

    private static List<Plane> planes = SpawnModel.generatePlanes();
    private AbstractAirStrip strip;
    private List<Point2D> planeCoordinates;

    private GraphicsContext gc;
    private GameEngineAreaTest engine;

    // Sezione EventHandler<Mouse>
    //private EventHandler<MouseEvent> handlerMouseReleased;
    //private EventHandler<MouseEvent> handlerMouseDragged;

    private static ParallelTransition parallelTransition = new ParallelTransition();
    private PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
    //private SequentialTransition sq = new SequentialTransition(parallelTransition, pauseTransition);

    public void playGame() {
        Platform.runLater(() -> {
            planes = SpawnModel.generatePlanes();

            List<ImageView> planeImages = planes.stream()
                    .map(Plane::getSprite)
                    .collect(Collectors.toUnmodifiableList());

            pane.getChildren().addAll(planeImages);
            //pane.getChildren().addAll(SpawnModel.generateIndicators());
            for (Plane plane : planes) {
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
        });
    }

    public synchronized void spawnPlane(final int numberPlaneSpawn) {
        List<SpawnLocation> spawnLocation = new ArrayList<>();
        Random r = new Random();

        for (SpawnLocation location:SpawnLocation.values()) {
            spawnLocation.add(location);
        }

        System.out.println("pane childern size" + pane.getChildren().size());

        for (int i = 0; i < numberPlaneSpawn; i++) {
            if (planes.size() < MAX_PLANE_TO_SPAWN) {
                int locationIndex = r.nextInt(spawnLocation.size());
                //System.out.println("Time to Spawn a Plane");
                Plane newPlane = new Plane("images/map_components/airplane.png");
                newPlane.connectToController(this);
                newPlane.getSprite().setFitWidth(64);
                newPlane.getSprite().setFitHeight(64);
                newPlane.playSpawnAnimation(spawnLocation.get(locationIndex));
                loadIndicatorAnimation(spawnLocation.get(locationIndex));
                System.out.println("side.random = " + spawnLocation.get(locationIndex));
                spawnLocation.remove(locationIndex);

                planes.add(newPlane);
                //planeInGame++;
                insertPlaneInMap(newPlane);
            }
        }
        System.out.println("");
    }

    public void loadIndicatorAnimation(final SpawnLocation side) {
        //ImageView indicatorImg = new ImageView("images/animations/indicator.png");
        //FadeTransition fadeTransition = new FadeTransition();

        final int delta = 50;

        final double width = pane.getWidth();
        final double height = pane.getHeight();

        final double halfWidth = width / 2.0;
        final double halfHeight = height / 2.0;

        switch (side) {
        case TOP:
            indicatorTop.setX(halfWidth);
            indicatorTop.setY(delta);

            fadeTop.setFromValue(1);
            fadeTop.setToValue(0);
            fadeTop.setDuration(Duration.millis(1000));
            fadeTop.setCycleCount(3);
            fadeTop.setNode(indicatorTop);

            fadeTop.play();
            indicatorTop.setVisible(true);


            break;

        case RIGHT:
            indicatorRight.setX(width - delta);
            indicatorRight.setY(halfHeight);

            fadeRight.setFromValue(1);
            fadeRight.setToValue(0);
            fadeRight.setDuration(Duration.millis(1000));
            fadeRight.setCycleCount(3);
            fadeRight.setNode(indicatorRight);

            fadeRight.play();
            indicatorRight.setVisible(true);

            break;

        case BOTTOM:
            indicatorBottom.setX(halfWidth);
            indicatorBottom.setY(height - delta);

            fadeBottom.setFromValue(1);
            fadeBottom.setToValue(0);
            fadeBottom.setDuration(Duration.millis(1000));
            fadeBottom.setCycleCount(3);
            fadeBottom.setNode(indicatorBottom);

            fadeBottom.play();
            indicatorBottom.setVisible(true);

            break;

        case LEFT:
            indicatorLeft.setX(delta);
            indicatorLeft.setY(halfHeight);

            fadeLeft.setFromValue(1);
            fadeLeft.setToValue(0);
            fadeLeft.setDuration(Duration.millis(1000));
            fadeLeft.setCycleCount(3);
            fadeLeft.setNode(indicatorLeft);

            fadeLeft.play();
            indicatorLeft.setVisible(true);
            break;

        default:
            break;
        }
    }

    @FXML
    public void initialize() {
        strip = new BasicAirStrip("images/map_components/airstrip.png", this);
        Game newGame = new Game();
        engine = new GameEngineAreaTest(this);

        engine.setLandingBoxLeft(landingBoxLeft);
        engine.setLandingBoxRight(landingBoxRight);

        newGame.startGame();
        gc = canvas.getGraphicsContext2D();
        planeCoordinates = new ArrayList<>();

        this.indicatorTop = new ImageView(new Image("images/animations/indicator.png"));
        this.indicatorLeft = new ImageView(new Image("images/animations/indicator.png"));
        this.indicatorRight = new ImageView(new Image("images/animations/indicator.png"));
        this.indicatorBottom = new ImageView(new Image("images/animations/indicator.png"));

        this.fadeTop = new FadeTransition();
        this.fadeLeft = new FadeTransition();
        this.fadeRight = new FadeTransition();
        this.fadeBottom = new FadeTransition();

        indicatorTop.setVisible(false);
        indicatorLeft.setVisible(false);
        indicatorRight.setVisible(false);
        indicatorBottom.setVisible(false);

        // A inizio partita si spawnerà 1 Plane alla volta, poi successivamente,
        // ogni 500 punti raggiunti numberPlanesToSpawnEachTime incrementerà di 1 fino
        // ad un massimo di 4 Plane alla volta.
        this.numberPlanesToSpawnEachTime = 1;

        // TEST inserimento Plane in Game dopo modifiche
        Plane p1 = new Plane(SpriteType.AIRPLANE);

        pane.getChildren().add(p1.getSprite());
        p1.connectToController(this);

        planes.add(p1);
        engine.setPlanes(planes);
        // FINE TEST --------------------------------------------------------------------------

        imgViewHelicopterLandingArea.setX(
                (pane.getBoundsInLocal().getWidth() / 2) - imgViewHelicopterLandingArea.getFitWidth()
                );
        imgViewHelicopterLandingArea.setY(
                (pane.getBoundsInLocal().getHeight() / 2) - imgViewHelicopterLandingArea.getFitHeight() / 2
                );

        strip.setAirStripImage(imgViewPlaneLandingArea);
        ((BasicAirStrip) strip).setBoxLeft(landingBoxLeft);
        ((BasicAirStrip) strip).setBoxRight(landingBoxRight);

        this.spawnCountDown = new Timeline(new KeyFrame(Duration.seconds(10), cycle -> {
            spawnPlane(numberPlanesToSpawnEachTime);
            System.out.println("numberPlanesToSpawnEachTime" + numberPlanesToSpawnEachTime);
        }));

        spawnCountDown.setCycleCount(Animation.INDEFINITE);
        //spawnCountDown.play();
        name.setText(MapController.getName());

        // TO-DO da decommentare altrimenti non spawnano i Plane
        //playGame();

        // Copiato da playGame!!!
        engine.setEngineStart(true);

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

        //-----------------------------------------------------
    }

    // TO-DO: NON Funziona da sostituire
    public void insertPlaneInMap(final Plane plane) {
        if (!pane.getChildren().contains(plane)) {
            pane.getChildren().add(plane.getSprite());
        }
    }

    public void terminateGame() {
        // Blocco del GameLoop
        engine.setEngineStart(false);
        parallelTransition.stop();

        // Terminazione di tutte le animazioni del Plane in corso
        GameController.stop();

        // Disattivazione EventHandlerMouse, NON FUNZIONANO, da rimettere!!!
        //canvas.removeEventFilter(MouseEvent.ANY, handlerMouseDragged);
        //canvas.removeEventFilter(MouseEvent.ANY, handlerMouseReleased);
        pane.setDisable(true);

        Platform.runLater(() -> {
            try {
                UserRecordsController.updateScore(name.getText(), getIntScore());
                TimeUnit.SECONDS.sleep(1);
                CommonView.showDialog(Page.GAMEOVER);
            } catch (IOException | InterruptedException e) {
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
        GameController.pause();
        parallelTransition.pause();
        CommonView.showDialog(Page.PAUSE);
    }

    public void startExplosionToPane(final ExplosionAnimation testExplosion, final Plane planeCollided) {
        Platform.runLater(() -> {
            pane.getChildren().add(testExplosion.getImgExplosion());
            testExplosion.getImgExplosion().setX(planeCollided.getSprite().getBoundsInParent().getCenterX());
            testExplosion.getImgExplosion().setY(planeCollided.getSprite().getBoundsInParent().getCenterY());
            testExplosion.startExplosion();
        });
    }

    public static List<Plane> getPlanes() {
        return planes;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void removePlanes(final Collection<? extends Plane> planes) {
        Platform.runLater(() -> pane.getChildren().removeAll(planes.stream()
                .map(Plane::getSprite)
                .collect(Collectors.toList())));
    }

    public synchronized void removePlane(final Plane plane) {
        Platform.runLater(() -> {
            System.out.println("Plane landing...");
            planes.removeIf(obj -> obj.hashCode() == plane.hashCode());

            Iterator<Plane> iter = planes.iterator();
            while (iter.hasNext()) {
                if (iter.next().hashCode() == plane.hashCode()) {
                    System.out.println("Plane remove: " + iter.hashCode());
                    iter.remove();
                }
            }

            engine.setPlanes(planes);
        });
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

    public static ParallelTransition getParallelTransition() {
        return parallelTransition;
    }

    public static Animation getOnePlanePT() {
        // TODO Auto-generated method stub
        return null;
    }
}
