package alt.sim.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.model.PlaneMovement;
import alt.sim.model.plane.Plane;
import javafx.animation.PathTransition;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransitionTest extends Application {
    private Pane paneRoot;
    private List<Plane> planes;
    private Plane plane;
    private Plane plane2;
    private Plane plane3;
    private Plane plane4;
    private Canvas canvas;

    private GameEngineAreaTest engine;
    private PathTransition pathTransition;
    private Path path;
    private List<Point2D> planeCoordinates;
    private GraphicsContext gc;

    // Area Collision
    private Rectangle rectangleWall;

    @Override
    public void start(final Stage stage) throws Exception {
        paneRoot = new Pane();
        canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        engine = new GameEngineAreaTest(this);

        pathTransition = new PathTransition();
        path = new Path();

        planeCoordinates = new ArrayList<Point2D>();

        planes = new ArrayList<>();
        plane = new Plane("images/map_components/airplane.png");
        plane2 = new Plane("images/map_components/airplane.png");
        plane3 = new Plane("images/map_components/airplane.png");
        plane4 = new Plane("images/map_components/airplane.png");

        plane.connetToController(this);
        plane2.connetToController(this);
        plane3.connetToController(this);
        plane4.connetToController(this);
        planes.add(plane);
        planes.add(plane2);
        planes.add(plane3);
        planes.add(plane4);

        rectangleWall = new Rectangle(800, 600, 40, 30);
        // Inizio funzionamento della View

        // Calculating the Proportion --> (Image:Screen)
        plane.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane2.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane3.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane4.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);

        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Insert Plane test into view:
        plane2.getImagePlane().setX(500);
        plane2.getImagePlane().setY(500);
        plane3.getImagePlane().setX(800);
        plane3.getImagePlane().setY(100);
        plane4.getImagePlane().setX(800);
        plane4.getImagePlane().setY(800);

        // Section Canvas + WallCollision + ImageTest
        paneRoot.getChildren().addAll(canvas, rectangleWall, plane.getImagePlane(), plane2.getImagePlane());
        paneRoot.getChildren().addAll(plane3.getImagePlane(), plane4.getImagePlane());

        // Section GraphicsContext
        engine.setGraphicContext(gc);
        engine.setPlanes(planes);
        gc = canvas.getGraphicsContext2D();

        // Avvio del GameLoop
        class ThreadEngine implements Runnable {

            @Override
            public void run() {
                engine.mainLoop();
            }
        }

        Thread t = new Thread(new ThreadEngine());
        t.start();

        //EventHandler<MouseEvent> handlerMouseMoved = event -> {gc.moveTo(event.getX(), event.getY());};

        EventHandler<MouseEvent> handlerMouseDragged = event -> {
            if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.BLUE);
                gc.stroke();
            }
        };

        EventHandler<MouseEvent> handlerMouseReleased = event -> {
            if (plane.getStatusRandomTransition() == "RUNNING-RANDOM") {
                plane.stopRandomTransition();
            }

            for (Plane planeSelected:planes) {

                if (planeSelected.getIsPlaneSelectedForBeenMoved()) {
                    if (planeSelected.getPlaneMovementAnimation() != null) {
                        planeSelected.stopPlaneMovementAnimation();
                    }

                    planeSelected.setPlaneLinesPath(planeCoordinates);
                    clearMap();
                    restoreLinesRemoved();

                    planeSelected.loadPlaneMovementAnimation();
                    planeSelected.startPlaneMovementAnimation();
                }
            }

            // 2)
            // Blocca il ciclo in GameEngineImpl per resettare le nuove coordinate.
            if (pathTransition.getStatus() == Status.RUNNING) {
                pathTransition.stop();
                //engine.stopPathTransition();
                //engine.setReadyToStart(false);
            }

            //planeMove.setPlaneCoordinatesList(clearListCoordinates(planeCoordinates));

            for (Plane planeSelected:planes) {
                copyCoordinatesInPath(planeSelected.getPlaneLinesPath());
            }

            selectedPathNode();
            clearPlaneSelectedForBeenMoved();

            // Quando viene rilasciato il Mouse le coordinate salvate vengono liberate
            planeCoordinates.clear();
        };

        EventHandler<KeyEvent>  handlerKeyPressed = event -> {
            if (event.getCode() == KeyCode.G) {
                plane.loadRandomTransition();
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, handlerKeyPressed);
        stage.setScene(scene);
        stage.show();
    }

    /*
     * public void randomMovement(final Plane planeMovedRandom) {
     * planeMovedRandom.setIsPlaneSelectedForBeenMoved(true); PathTransition
     * transitionRandom = new PathTransition(); Path pathRandom = new Path(); Random
     * r = new Random();
     * 
     * int randomX = r.nextInt(1400); int randomY = r.nextInt(1000);
     * 
     * pathRandom.getElements().add(new
     * MoveTo(planeMovedRandom.getImagePlane().getBoundsInParent().getMinX(),
     * planeMovedRandom.getImagePlane().getBoundsInParent().getMinY()));
     * pathRandom.getElements().add(new LineTo(randomX, randomY));
     * 
     * transitionRandom.setPath(pathRandom);
     * transitionRandom.setNode(planeMovedRandom.getImagePlane());
     * transitionRandom.setDuration(Duration.seconds(10));
     * transitionRandom.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
     * 
     * gc.moveTo(planeMovedRandom.getImagePlane().getBoundsInParent().getMinX(),
     * planeMovedRandom.getImagePlane().getBoundsInParent().getMinY());
     * gc.lineTo(randomX, randomY); gc.setStroke(Color.GREEN); gc.stroke();
     * 
     * transitionRandom.play(); transitionRandom.setOnFinished(event ->
     * planeMovedRandom.setIsPlaneSelectedForBeenMoved(false)); }
     */
    public void clearMap() {
        final double dimensionRectangleCleanerWidth = 2000;
        final double dimensionRectangleCleanerHeight = 2000;

        gc.clearRect(0, 0, dimensionRectangleCleanerWidth, dimensionRectangleCleanerHeight);
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

    private void selectedPathNode() {
        for (Plane planeSelected:planes) {

            if (planeSelected.getIsPlaneSelectedForBeenMoved()) {
                planeSelected.setIsPlaneSelectedForBeenMoved(false);
            } 
            planeSelected.setIsPlaneSelectedForBeenMoved(false);
        }
    }

    public void clearPlaneSelectedForBeenMoved() {
        for (Plane planeSelected:planes) {
            planeSelected.setIsPlaneSelectedForBeenMoved(false);
        }
    }

    private void copyCoordinatesInPath(final List<Point2D> planeCoordinates) {
        // Ripuliamo le coordinate presenti dal path prima
        path = new Path();

        for (int k = 0; k < planeCoordinates.size(); k++) {

            if (k == 0) {
                path.getElements().add(new MoveTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()));
            } else {
                path.getElements().add(new LineTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()));
            }
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }

    public void clearPlaneCoordinatesAndUpdate(final int idPlane) {
        for (Plane planeSelected:planes) { 
            planeSelected.resetPlaneLinesPath(idPlane);
        }
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public List<Plane> getPlanes() {
        return this.planes;
    }

    public Rectangle getRectangleWall() {
        return this.rectangleWall;
    }

    public ImageView getPlaneSprite() {
        return this.plane.getImagePlane();
    }
}
