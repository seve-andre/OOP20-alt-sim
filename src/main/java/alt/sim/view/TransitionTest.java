package alt.sim.view;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import alt.sim.model.ImageClassification;
import alt.sim.model.PlaneMovement;
import alt.sim.model.plane.Plane;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransitionTest extends Application {
    private PlaneMovement planeMove;
    private Plane plane;

    private PathTransition pathTransition;
    private Path path;
    private List<Point2D> planeCoordinates;

    @Override
    public void start(final Stage stage) throws Exception {
        Pane paneRoot = new Pane();
        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        pathTransition = new PathTransition();
        path = new Path();

        planeCoordinates = new ArrayList<Point2D>();
        planeMove = new PlaneMovement();
        plane = new Plane(ImageClassification.AIRPLANE);

        // Calculating the Proportion --> (Image:Screen)
        plane.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        // Insert Plane test into view:
        paneRoot.getChildren().add(plane.getSpritePlane().getImageSpriteResized().getImageSprite());
        // Section Canvas
        paneRoot.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {
                gc.moveTo(event.getX(), event.getY());
            }
        };

        EventHandler<MouseEvent> handlerMouseClicked = new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {

                // EVENTO CLICK DA RIVEDERE IL FUNZIONAMENTO!!!!
                // INCASINA TUTTO L'algoritmo
                /*
                 * if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                 * planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                 * gc.lineTo(event.getX(), event.getY()); gc.setStroke(Paint.valueOf("green"));
                 * gc.stroke(); }
                 */

            }
        };

        EventHandler<MouseEvent> handlerMouseDragged = new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {

                if (planeCoordinates.size() < PlaneMovement.COORDINATES_LIMIT) {
                     planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                     gc.lineTo(event.getX(), event.getY());
                     gc.setStroke(Color.LIMEGREEN);
                     gc.stroke();
                }
            }
        };

        EventHandler<MouseEvent> handlerMouseReleased = new EventHandler<MouseEvent>() {

            @Override public void handle(final MouseEvent event) {
                // 2)
                // Blocca il ciclo in GameEngineImpl per resettare le nuove coordinate.
                if (pathTransition.getStatus() == Status.RUNNING) {
                    pathTransition.stop();

                    // Per ripulire il canvas dal percorso non più utilizzato.
                    //gc.clearRect(0, 0, 1900, 1400);
                }

                planeMove.setPlaneCoordinatesList(clearListCoordinates(planeCoordinates));
                planeMove.printPlaneCoordinates();

                // Impostando l'animazione del movimento dell'aereo
                // Da inserire in un METODO!!!
                copyCoordinatesInPath(planeCoordinates);
                pathTransition = new PathTransition();
                pathTransition.setPath(path);
                pathTransition.setNode(plane.getImagePlane());
                pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.setDuration(Duration.millis(10000));
                pathTransition.play();

                // Quando viene rilasciato il Mouse le coordinate salvate vengono liberate
                planeCoordinates.clear();
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerMouseClicked);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
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

    private List<Point2D> clearListCoordinates(final List<Point2D> planeCoordinates) {
        Set<Point2D> s = new LinkedHashSet<>(planeCoordinates);
        List<Point2D> copyListCleaned = new ArrayList<Point2D>(s);
        //System.out.println("Lunghezza copyListCleaned: " + copyListCleaned.size());

        return copyListCleaned;
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
