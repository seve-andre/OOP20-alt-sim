package alt.sim.view.seaside;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SeasideTest extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.resize(1000, 1000);

        Scene scene = new Scene(pane, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(final String[] args) {
        launch(args);
    }
}
