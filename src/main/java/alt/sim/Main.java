package alt.sim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Rectangle2D;


public class Main extends Application {

    private static Stage pStage;

    @Override
    public void start(final Stage stage) throws Exception {

        setStage(stage);

        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/Loading.fxml"));
        stage.setTitle("AirLine Traffic Simulator");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(ClassLoader.getSystemResource("images/logos/icon.png").toExternalForm()));
        stage.setResizable(false);

        stage.show();
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(final String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return pStage;
    }

    private void setStage(final Stage stage) {
        Main.pStage = stage;
    }
}
