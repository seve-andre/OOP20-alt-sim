package alt.sim.view;

import alt.sim.model.ImageClassification;
import alt.sim.model.plane.Plane;
import javafx.application.Application;
import javafx.event.EventHandler;
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

    @Override
    public void start(final Stage stage) throws Exception {
        Pane paneRoot = new Pane();

        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        Plane p1 = new Plane(ImageClassification.AIRPLANE);

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
               drawShapes(gc, event.getX(), event.getY());
             } 
          };

         EventHandler<MouseEvent> handlerMouseReleased = new EventHandler<MouseEvent>() { 

             @Override 
             public void handle(final MouseEvent event) { 
               p1.getSpritePlane().setX(event.getX());
               p1.getSpritePlane().setY(event.getY());

               //Insert Center Image when click
               centerImagePositionInGame(p1, event);
             } 
          };

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

}
