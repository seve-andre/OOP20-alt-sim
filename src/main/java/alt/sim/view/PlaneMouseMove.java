package alt.sim.view;

import java.util.ArrayList;
import java.util.List;

import alt.sim.controller.engine.GameEngineImpl;
import alt.sim.model.ImageClassification;
import alt.sim.model.PlaneMovement;
import alt.sim.model.plane.Plane;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * Controlling the Position of the Plane in the Map using MouseEvent controls.
 *
 */
public class PlaneMouseMove extends Application {

    private PlaneMovement planeMove;
    private Plane p1;
    private PathTransition pathTransition = new PathTransition();
    private Path path = new Path();
    public PlaneMouseMove() {
        p1 = new Plane(ImageClassification.AIRPLANE);
        planeMove = new PlaneMovement();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        Pane paneRoot = new Pane();
        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        GameEngineImpl engine = new GameEngineImpl(this);
        class ThreadEngine implements Runnable {
            @Override
            public void run() {
                engine.mainLoop();
            }
        }
        Thread t = new Thread(new ThreadEngine());
        //Create ArrayList for manage the coordinates of Plane
        List<Point2D> planeCoordinates = new ArrayList<Point2D>();

        // Calculating the Proportion --> (Image:Screen)
        p1.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);
        p1.getSpritePlane().getImageSpriteResized().getImageSprite().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);;

        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Insert Plane test into view:
        paneRoot.getChildren().add(p1.getSpritePlane().getImageSpriteResized().getImageSprite());

        // Section Canvas
        paneRoot.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

         EventHandler<MouseEvent> handlerMousePressed = new EventHandler<MouseEvent>() {

             @Override
             public void handle(final MouseEvent event) {
                 drawShapes(gc, event.getX(), event.getY());
             }
         };

         EventHandler<MouseEvent> handlerMouseDragged = new EventHandler<MouseEvent>() {

             @Override
             public void handle(final MouseEvent event) {
                 planeCoordinates.add(new Point2D(event.getX(), event.getY()));
                 drawShapes(gc, event.getX(), event.getY());
             }
          };

         EventHandler<MouseEvent> handlerMouseReleased = new EventHandler<MouseEvent>() {

             @Override
             public void handle(final MouseEvent event) {
                 planeMove.setPlaneCoordinates(planeCoordinates);
                 planeMove.printPlaneCoordinates();
                 engine.setStart(true);
                 Path path = new Path();
                 path.getElements().add(new MoveTo(0f, 50f));
                 //path.getElements().add(new MoveTo(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY()));
                 //pathTransition.setDuration(Duration.millis(10000));
                 pathTransition.setNode(p1.getImagePlane());
                 //path.getElements().add(new MoveTo(0f, 50f));

                 /*for (int i = 0; i < planeCoordinates.size(); i++) {
                     path.getElements().add(new LineTo(planeCoordinates.get(i).getX(), planeCoordinates.get(i).getY()));
                 }*/

                 System.out.println("Rotate Plane get from PathTransition = " + p1.getImagePlane().getRotate());
                 /*pathTransition.setDuration(Duration.millis(10000));
                 pathTransition.setNode(p1.getImagePlane());
                 pathTransition.setPath(path);
                 pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
                 pathTransition.play();*/

                 //engine.setStart(true);
                 //p1.getSpritePlane().setX(event.getX());
                 //p1.getSpritePlane().setY(event.getY());

                 //Insert Center Image when click
                 //centerImagePositionInGame(p1, event);
                 //Clear the List after catched the points
                 planeCoordinates.clear();
             }
          };
        t.start();
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handlerMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();

    }

    private void centerImagePositionInGame(final Plane planeInGame, final MouseEvent event) {
        //Insert Center Image when click
        planeInGame.getImagePlane().setLayoutX(event.getX() - (planeInGame.getImagePlane().getFitWidth() / 2));
        planeInGame.getImagePlane().setLayoutY(event.getY() - (planeInGame.getImagePlane().getFitHeight() / 2));
    }

    private void drawShapes(final GraphicsContext gc, final double x, final double y) {
        gc.lineTo(x, y);
        gc.stroke();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    public PlaneMovement getPlaneMovement() {
        return this.planeMove;
    }

    public Plane getPlane() {
        return this.p1;
    }

    public void startTransiction(final Double x, final Double y) {
        path.getElements().add(new MoveTo(x, y));
        pathTransition.setDuration(Duration.millis(400));
        pathTransition.setPath(path);
        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }
}
