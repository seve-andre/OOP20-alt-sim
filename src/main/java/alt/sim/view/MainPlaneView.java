package alt.sim.view;

import alt.sim.model.ImageResized;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * View class for show the Plane.
 */
public class MainPlaneView extends Application {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 768;

    @Override
    public void start(final Stage stage) throws Exception {

        try {
            Pane paneRoot = new Pane();
            ImageResized planeImageResized = new ImageResized();

            // Calculating the Proportion --> (Image:Screen)
            planeImageResized.resizeImageSprite();

            // View Plane demonstrating:
            paneRoot.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
            paneRoot.getChildren().add(planeImageResized.getImageSprite());

            Scene scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
            stage.setScene(scene);
            stage.show();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch a standalone application calling the JavaFx mainThread.
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
