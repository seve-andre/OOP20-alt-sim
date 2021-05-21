package alt.sim.view.airstrip;

import alt.sim.model.ImageResized;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BaicAirStripView extends Application {
    /** Screen width of the view, calling Screen class that return the size of the Primary screen.  */
    private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    /** Screen height of the view.  */
    private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static final double SCREEN_PROPORTION = 0.6;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            Pane paneRoot = new Pane();
            ImageResized planeAirStripResized = new ImageResized("images/map_components/airstrip.png");

            // View Plane demonstrating:
            paneRoot.resize(SCREEN_WIDTH * SCREEN_PROPORTION, SCREEN_HEIGHT * SCREEN_PROPORTION);
            paneRoot.getChildren().add(planeAirStripResized.getImageSprite());

            Scene scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(final String[] args) {
        launch(args);
    }
}
