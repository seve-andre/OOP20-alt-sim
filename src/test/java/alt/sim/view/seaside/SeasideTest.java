package alt.sim.view.seaside;

import alt.sim.model.plane.PlaneTest;
import alt.sim.model.plane.UserTransitionTest;
import alt.sim.model.sprite.SpriteType;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SeasideTest extends Application {

    @Override
    public void start(final Stage stage) {
        Pane pane = new Pane();
        Canvas canvas = new Canvas(1000, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        UserTransitionTest usrTransition = new UserTransitionTest();
        List<Point2D> planeCoordinates = new ArrayList<>();

        pane.resize(1000, 1000);
        
        Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(final String[] args) {
        launch(args);
    }
}
