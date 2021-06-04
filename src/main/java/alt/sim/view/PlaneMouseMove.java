package alt.sim.view;

import java.util.ArrayList;
import java.util.List;

import alt.sim.controller.engine.GameEngineImpl;
import alt.sim.model.ImageClassification;
import alt.sim.model.PlaneMovement;
import alt.sim.model.plane.Plane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Controlling the Position of the Plane in the Map using MouseEvent controls.
 *
 */
public class PlaneMouseMove extends Application {

    private PlaneMovement planeMove;
    private Plane p1;
    @Override
    public void start(final Stage stage) throws Exception {
        Pane paneRoot = new Pane();
        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        p1 = new Plane(ImageClassification.AIRPLANE);
        planeMove = new PlaneMovement();
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


        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Insert Plane test into view:
        paneRoot.getChildren().add(p1.getSpritePlane().getImageSpriteResized().getImageSprite());

        // Section Canvas
        paneRoot.getChildren().add(canvas);
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

                 p1.getSpritePlane().setX(event.getX());
                 p1.getSpritePlane().setY(event.getY());

                 //Insert Center Image when click
                 centerImagePositionInGame(p1, event);
                 //Clear the List after catched the points
                 planeCoordinates.clear();
             }
          };

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handlerMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
        t.start();
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
}
