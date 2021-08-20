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
        PlaneTest p1 = new PlaneTest(SpriteType.AIRPLANE);
        usrTransition.setPlaneUsed(p1);

        pane.getChildren().addAll(canvas, p1.getSprite());

        stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            planeCoordinates.add(new Point2D(event.getX(), event.getY()));
            gc.lineTo(event.getX(), event.getY());
            gc.setStroke(Color.BLUE);
            gc.stroke();
        });

        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            p1.setPlaneLinesPath(planeCoordinates);
            usrTransition.loadUserTransition();
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
        });

        Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(final String[] args) {
        launch(args);
    }
}
