package alt.sim.view.airstrip;

import alt.sim.model.calculation.ImageSprite;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class is the view for the airstrips.
 */
public class AirStripView extends Application {
    /** Screen width of the view, calling Screen class that return the size of the Primary screen.  */
    private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    /** Screen height of the view.  */
    private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static final double SCREEN_PROPORTION = 0.8;
    private String url;

    /**
     * Constructor method for AirStripView.
     * @param url the url of the airstrip
     */
    AirStripView(final String url) {
        this.url = url;
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            Pane paneRoot = new Pane();
            ImageSprite planeAirStripResized = new ImageSprite(this.url);

            // View Plane demonstrating:
            paneRoot.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
            paneRoot.getChildren().add(planeAirStripResized.getImageSprite());

            Scene scene = new Scene(paneRoot, SCREEN_WIDTH * SCREEN_PROPORTION,  SCREEN_HEIGHT * SCREEN_PROPORTION);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
