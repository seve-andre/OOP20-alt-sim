package alt.sim.view;

import java.io.IOException;

import alt.sim.model.ImageClassification;
import alt.sim.model.plane.Plane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
    private static final double ASPECT_RATIO_DIVISION = 1;

    @Override
    public void start(final Stage stage) throws Exception {

        try {
            Pane paneRoot = new Pane();
            Plane p1 = new Plane(ImageClassification.AIRPLANE);

            // Calculating the Proportion --> (Image:Screen)
            p1.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);

            // View Plane demonstrating:
            paneRoot.resize(SCREEN_WIDTH, SCREEN_HEIGHT);

            // Insert Plane test into view:
            paneRoot.getChildren().add(p1.getSpritePlane().getImageSpriteResized().getImageSprite());


            // Positioning the STATIC Plane in a specific location of the Map
            p1.getSpritePlane().setX(0);
            p1.getSpritePlane().setY(0);

            // Create a MouseEvent for move the Plane in the Position clicked
            EventHandler<MouseEvent> handlerMouseClick = new EventHandler<MouseEvent>() {

                @Override
                public void handle(final MouseEvent event) {
                    p1.getSpritePlane().setX(event.getX());
                    p1.getSpritePlane().setY(event.getY());

                    p1.getImagePlane().setVisible(true);

                    // START Landing Animation
                    //p1.getLandingAnimation().play();

                    // START Explosion Animation
                    try {
                        p1.getExplosionAnimation();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    //System.out.println("X e Y: " + event.getX() + " , " + event.getY());
                    //Insert Center Image when click
                    centerImagePositionInGame(p1, event);
                }
             };

            paneRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerMouseClick);

            Scene scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void centerImagePositionInGame(final Plane planeInGame, final MouseEvent event) {
        //Insert Center Image when click
        planeInGame.getImagePlane().setLayoutX(event.getX() - (planeInGame.getImagePlane().getFitWidth() / 2));
        planeInGame.getImagePlane().setLayoutY(event.getY() - (planeInGame.getImagePlane().getFitHeight() / 2));
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
