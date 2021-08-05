package alt.sim.view.seaside;

import alt.sim.controller.map.MapController;
import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.controller.game.GameController;
import alt.sim.controller.user.records.UserRecordsController;
import alt.sim.model.animation.ExplosionAnimation;
import alt.sim.model.plane.PlaneMovement;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.BasicAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.spawn.SpawnLocation;
import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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

    @FXML
    void handlerMouseDragged(final MouseEvent event) {
        if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
            planeCoordinates.add(new Point2D(event.getX(), event.getY()));
            gc.lineTo(event.getX(), event.getY());
            gc.setStroke(Color.BLUE);
            gc.stroke();
        }
    }

    @FXML
    void handlerMouseReleased(final MouseEvent event) {
        Point2D puntoInizioPercorso;
        double distanzaDalPlane = 0;

        for (Plane planeSelected:planes) {
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

    private static final int SPAWN_DELAY = 6;
    /**
     * Campo contenente le informazioni globali sullo Schermo della Partita.
     */
    public static final Screen SCREEN_GAME = Screen.getPrimary();

    /**
     * Informazioni globali sui Bound dello Schermo.
     */
    public static final Rectangle2D SCREEN_BOUND = SCREEN_GAME.getVisualBounds();

    // Section Spawn Plane
    private static final int MAX_PLANE_TO_SPAWN = 4;

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
    private List<PathTransition> pathTransitionList2 = Plane.getPathTransitionList();
    private List<SpawnLocation> spawnLocationList = new ArrayList<>();

    private List<Plane> planes = new ArrayList<>();
    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;
    private List<Point2D> planeCoordinates;

    private GraphicsContext gc;
    private GameEngineAreaTest engine;
    private GameController gameController = new GameController(this);

    // Sezione EventHandler<Mouse>
    //private EventHandler<MouseEvent> handlerMouseReleased;
    //private EventHandler<MouseEvent> handlerMouseDragged;

    private static ParallelTransition parallelTransition = new ParallelTransition();

    /*public void playGame() {
        Platform.runLater(() -> {
            planes = SpawnModel.generatePlanes();

            List<ImageView> planeImages = planes.stream()
                    .map(Plane::getSprite)
                    .collect(Collectors.toUnmodifiableList());

            pane.getChildren().addAll(planeImages);
            //pane.getChildren().addAll(SpawnModel.generateIndicators());
            for (Plane plane:planes) {
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
            //parallelTransition.play();

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
            //t.start();
        });
    }*/

    public synchronized void spawnPlane(final int numberPlaneSpawn) {
        Random r = new Random();

        List<SpawnLocation> spawnLocation = new ArrayList<>(Arrays.asList(SpawnLocation.values()));

        for (int i = 0; i < numberPlaneSpawn; i++) {
            if (planes.size() < MAX_PLANE_TO_SPAWN) {
                int locationIndex = r.nextInt(spawnLocation.size());
                Plane newPlane = new Plane("images/map_components/airplane.png");
                newPlane.connectToController(this);
                newPlane.getSprite().setFitWidth(64);
                newPlane.getSprite().setFitHeight(64);
                newPlane.playSpawnAnimation(spawnLocation.get(locationIndex));
                loadIndicatorAnimation(spawnLocation.get(locationIndex));
                System.out.println("side.random = " + spawnLocation.get(locationIndex));
                spawnLocation.remove(locationIndex);

                planes.add(newPlane);
                pane.getChildren().add(newPlane.getSprite());
                //insertPlaneInMap(newPlane);
            }
        }
        engine.setPlanes(planes);
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

                this.setFadeTransition(fadeTop, indicatorTop);
                indicatorTop.setVisible(true);
                break;

            case RIGHT:
                indicatorRight.setX(width - delta);
                indicatorRight.setY(halfHeight);

                this.setFadeTransition(fadeRight, indicatorRight);
                indicatorRight.setVisible(true);
                break;

            case BOTTOM:
                indicatorBottom.setX(halfWidth);
                indicatorBottom.setY(height - delta);

                this.setFadeTransition(fadeBottom, indicatorBottom);
                indicatorBottom.setVisible(true);
                break;

            case LEFT:
                indicatorLeft.setX(delta);
                indicatorLeft.setY(halfHeight);

                this.setFadeTransition(fadeLeft, indicatorLeft);
                indicatorLeft.setVisible(true);
                break;

            default:
                break;
        }
    }

    @FXML
    public void initialize() {
        stripLeft = new BasicAirStrip("images/map_components/singleAirstrip.png", this);
        stripRight = new BasicAirStrip("images/map_components/singleAirstrip.png", this);
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

        List<ImageView> indicatorList = List.of(indicatorTop, indicatorRight, indicatorBottom, indicatorLeft);
        indicatorList.forEach(indicator -> indicator.setVisible(false));
        pane.getChildren().addAll(indicatorList);

        // A inizio partita si spawnerà 1 Plane alla volta, poi successivamente,
        // ogni 500 punti raggiunti numberPlanesToSpawnEachTime incrementerà di 1 fino
        // ad un massimo di 4 Plane alla volta.
        this.numberPlanesToSpawnEachTime = 1;

        imgViewHelicopterLandingArea.setX(
                (pane.getBoundsInLocal().getWidth() / 2) - imgViewHelicopterLandingArea.getFitWidth()
        );
        imgViewHelicopterLandingArea.setY(
                (pane.getBoundsInLocal().getHeight() / 2) - imgViewHelicopterLandingArea.getFitHeight() / 2
        );

        stripLeft.setAirStripImage(imgViewPlaneLandingArea);
        stripRight.setAirStripImage(imgViewPlaneLandingArea);
        ((BasicAirStrip) stripLeft).setBox(landingBoxLeft);
        ((BasicAirStrip) stripRight).setBox(landingBoxRight);

        this.spawnCountDown = new Timeline(new KeyFrame(Duration.seconds(SPAWN_DELAY), cycle -> {
            spawnPlane(numberPlanesToSpawnEachTime);
            System.out.println("numberPlanesToSpawnEachTime" + numberPlanesToSpawnEachTime);
        }));

        spawnCountDown.setCycleCount(Animation.INDEFINITE);
        spawnCountDown.play();
        name.setText(MapController.getName());

        // TO-DO da decommentare altrimenti non spawnano i Plane
        //playGame();

        // Copiato da playGame!!!
        engine.setEngineStart(true);
        engine.setPlanes(planes);

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

    // TO-DO: NON Funziona da sostituire
    public void insertPlaneInMap(final Plane plane) {
        if (!pane.getChildren().contains(plane.getSprite())) {
            pane.getChildren().add(plane.getSprite());
        }
    }

    public void terminateGame() {
        // Blocco del GameLoop
        engine.setEngineStart(false);
        pathTransitionList2.forEach(pathTransition -> {
            if (!pathTransition.getStatus().equals(Status.STOPPED)) {
                pathTransition.stop();
            }
        });

        // Terminazione di tutte le animazioni del Plane in corso
        gameController.stop();

        // Disattivazione EventHandlerMouse, NON FUNZIONANO, da rimettere!!!
        //canvas.removeEventFilter(MouseEvent.ANY, handlerMouseDragged);
        //canvas.removeEventFilter(MouseEvent.ANY, handlerMouseReleased);
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
        gameController.pause();
        pathTransitionList2.forEach(Animation::pause);
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

    public List<Plane> getPlanes() {
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

    public AbstractAirStrip getStripLeft() {
        return this.stripLeft;
    }

    public AbstractAirStrip getStripRight() {
        return this.stripRight;
    }

    public AnchorPane getPane() {
        return this.pane;
    }

    public void addScore(final int score) {
        this.score.setText(String.valueOf(Integer.parseInt(this.score.getText()) + score));
    }

    public int getIntScore() {
        return Integer.parseInt(this.score.getText());
    }

    public static ParallelTransition getOnePlanePT() {
        return parallelTransition;
    }

    public void setNumberPlanesToSpawn(final int numberPlanesToSpawn) {
        this.numberPlanesToSpawnEachTime = numberPlanesToSpawn;
    }

    public void setFadeTransition(final FadeTransition fade, final ImageView indicator) {
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(3);
        fade.setNode(indicator);

        fade.play();
    }

    public void addTransition(final PathTransition pathTransition2) {
        this.pathTransitionList2.add(pathTransition2);
    }

    public Timeline getSpawnCountDown() {
        return this.spawnCountDown;
    }

    public GameController getGameController() {
        return this.gameController;
    }
}
