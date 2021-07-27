package alt.sim.view;

import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.model.ExplosionAnimation;
import alt.sim.model.PlaneMovement;
import alt.sim.model.plane.Plane;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void start(final Stage stage) throws Exception {
        paneRoot = new Pane();
        canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        //engine = new GameEngineAreaTest(this);

        //pathTransition = new PathTransition();
        //path = new Path();

        planeCoordinates = new ArrayList<>();

        planes = new ArrayList<>();
        plane = new Plane("images/map_components/airplane.png");
        plane2 = new Plane("images/map_components/airplane.png");
        plane3 = new Plane("images/map_components/airplane.png");
        plane4 = new Plane("images/map_components/airplane.png");

        plane.connectToController(this);
        plane2.connectToController(this);
        plane3.connectToController(this);
        plane4.connectToController(this);
        planes.add(plane);
        planes.add(plane2);
        planes.add(plane3);
        planes.add(plane4);

        // Inizio funzionamento della View
        // Calculating the Proportion --> (Image:Screen)
        plane.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane2.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane3.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        plane4.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);

        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Insert Plane test into view:
        plane2.getImagePlane().setX(900);
        plane2.getImagePlane().setY(500);
        plane3.getImagePlane().setX(1000);
        plane3.getImagePlane().setY(100);
        plane4.getImagePlane().setX(600);
        plane4.getImagePlane().setY(600);

        // Section Canvas + WallCollision + ImageTest
        paneRoot.getChildren().addAll(canvas, plane.getImagePlane(), plane2.getImagePlane());
        paneRoot.getChildren().addAll(plane3.getImagePlane(), plane4.getImagePlane());

        // Section GraphicsContext
        //engine.setGraphicContext(gc);
        engine.setPlanes(planes);
        gc = canvas.getGraphicsContext2D();

        /*
         * Bounds beforeBounds = plane.getImagePlane().getBoundsInParent();
         *
         * System.out.println("getRotate: " + plane2.getImagePlane().getRotate());
         * plane2.getImagePlane().setRotate(20); System.out.println("getRotate: " +
         * plane2.getImagePlane().getRotate());
         *
         * for (Plane plane:planes) { Bounds bounds =
         * plane.getImagePlane().getBoundsInParent();
         *
         * System.out.println("Bounds: " + bounds.getMinX() + " , " + bounds.getMinY() +
         * " , " + bounds.getWidth() + " , " + bounds.getHeight());
         * gc.strokeRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(),
         * bounds.getHeight()); }
         */

        // Avvio del GameLoop
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

        // Vengono campionate le coordinate che dovrà seguire il Plane
        // E viene disegnata la rotta tracciata
        EventHandler<MouseEvent> handlerMouseDragged = event -> {
            if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.BLUE);
                gc.stroke();
            }
        };

        EventHandler<MouseEvent> handlerMouseReleased = event -> {
            Point2D puntoInizioPercorso;
            double distanzaDalPlane = 0;


            for (Plane planeSelected : planes) {

                //Controllo che l'utente disegni un percorso con un minimo di punti
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

            // 2)
            /*
             * if (pathTransition.getStatus() == Status.RUNNING) { pathTransition.stop(); }
             */
            /*
             * for (Plane planeSelected:planes) {
             * copyCoordinatesInPath(planeSelected.getPlaneLinesPath()); }
             */

            // Quando viene rilasciato il Mouse le coordinate salvate vengono liberate
            planeCoordinates.clear();
        };

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
    }

    public boolean isMoreThanOneSelected() {
        int planeBeenSelected = 0;

        for (Plane planeSelected:planes) {
            if (planeSelected.getIsPlaneSelectedForBeenMoved()) {
                planeBeenSelected++;
            }
        }

        if (planeBeenSelected >= 2) {
            return true;
        }

        return false;
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

    public void clearPlaneSelectedForBeenMoved() {
        for (Plane planeSelected:planes) {
            planeSelected.setIsPlaneSelectedForBeenMoved(false);
        }
    }

    /*
     * private void copyCoordinatesInPath(final List<Point2D> planeCoordinates) { //
     * Ripuliamo le coordinate presenti dal path prima path = new Path();
     *
     * for (int k = 0; k < planeCoordinates.size(); k++) {
     *
     * if (k == 0) { path.getElements().add(new
     * MoveTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY())); }
     * else { path.getElements().add(new LineTo(planeCoordinates.get(k).getX(),
     * planeCoordinates.get(k).getY())); } } }
     */

    public static void main(final String[] args) {
        launch(args);
    }

    public void clearPlaneCoordinatesAndUpdate(final int idPlane) {
        for (Plane planeSelected:planes) {
            planeSelected.resetPlaneLinesPath(idPlane);
        }
    }

    public void startExplosionToPane(final ExplosionAnimation testExplosion, final Plane planeCollided) {
        Platform.runLater(() -> {
            paneRoot.getChildren().add(testExplosion.getImgExplosion());
            testExplosion.getImgExplosion().setX(planeCollided.getImagePlane().getBoundsInParent().getCenterX());
            testExplosion.getImgExplosion().setY(planeCollided.getImagePlane().getBoundsInParent().getCenterY());
            testExplosion.startExplosion();
        });
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public List<Plane> getPlanes() {
        return this.planes;
    }

    public ImageView getPlaneSprite() {
        return this.plane.getImagePlane();
    }
}
