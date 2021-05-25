package alt.sim.view;

import alt.sim.model.ImageResized;
import alt.sim.model.plane.Plane;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * View class for show the Plane.
 */
public class MainPlaneView extends Application {
    /** Screen width of the view, calling Screen class that return the size of the Primary screen.  */
    private static final double SCREEN_WIDTH = (Screen.getPrimary().getBounds().getWidth() / MainPlaneView.ASPECT_RATIO_DIVISION);
    /** Screen height of the view.  */
    private static final double SCREEN_HEIGHT = (Screen.getPrimary().getBounds().getHeight() / MainPlaneView.ASPECT_RATIO_DIVISION);
    /** Number used to divide the Screen size. */
    private static final double ASPECT_RATIO_DIVISION = 1.5;

    @Override
    public void start(final Stage stage) throws Exception {

        try {
            Pane paneRoot = new Pane();
            ImageResized planeImageResized = new ImageResized("images/map_components/airplane.png");
            Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
            Plane p1 = new Plane("images/map_components/airplane.png", new Point2D(50, 50));

            p1.getSpritePlane().getImageSpriteResized().resizeImageSprite();
            // Calculating the Proportion --> (Image:Screen)
            planeImageResized.resizeImageSprite();

            // View Plane demonstrating:
            paneRoot.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
            //paneRoot.getChildren().add(planeImageResized.getImageSprite());
            // Insert Plane test into view:
            paneRoot.getChildren().add(p1.getSpritePlane().getImageSpriteResized().getImageSprite());

            // Positioning the STATIC Plane in a specific location of the Map
            p1.setX(0);
            p1.setY(0);

            // Section Canvas
            paneRoot.getChildren().add(canvas);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Create a MouseEvent
            EventHandler<MouseEvent> handlerMouseClick = new EventHandler<MouseEvent>() { 

                @Override 
                public void handle(final MouseEvent event) { 
                    p1.setX(event.getX());
                    p1.setY(event.getY());

                    p1.getImagePlane().setLayoutX(p1.getX());
                    p1.getImagePlane().setLayoutY(p1.getY());
                } 
             };

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


            paneRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerMouseClick);
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handlerMousePressed); 
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged); 

            Scene scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    private void drawShapes(final GraphicsContext gc, final double x, final double y) {
        gc.lineTo(x, y);
        gc.stroke();
    }

    /**
     * Launch a standalone application calling the JavaFx mainThread.
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    public static double getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static double getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
