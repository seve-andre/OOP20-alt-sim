package alt.sim.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LaunchPlane extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        final double widthScreen = 500;
        final double heightScreen = 500;

        BorderPane root = FXMLLoader.load(getClass().getResource("./plane/PlaneMovement.fxml"));

        Scene scene = new Scene(root, widthScreen, heightScreen);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
