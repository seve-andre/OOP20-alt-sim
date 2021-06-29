package alt.sim.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.stage.Stage;

public class LineToDrawingExample extends Application {
    private static final double CLEAR_RECTANGLE_WIDTH = 2000;
    private static final double CLEAR_RECTANGLE_HEIGHT = 2000;

    private boolean isBeginPathReady = true;

    @Override
    public void start(final Stage stage) throws Exception {
        Pane paneRoot = new Pane();
        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = canvas.getGraphicsContext2D();

        gc2.lineTo(500, 900);
        gc2.lineTo(900, 900);
        gc2.stroke();
        
        Line line1 = new Line(500, 900,50,50);
        Line line2 = new Line(900, 900,50,50);
        
        paneRoot.getChildren().addAll(line1, line2);
        paneRoot.getChildren().remove(line1);
        paneRoot.getChildren().remove(line2);


        EventHandler<MouseEvent> handlerMouseMoved = event -> gc.moveTo(event.getX(), event.getY());

        EventHandler<MouseEvent> handlerMouseDragged = event -> {
                if (isBeginPathReady) {
                    gc.beginPath();
                    gc.save();
                    isBeginPathReady = false;
                }

                gc.lineTo(event.getX(), event.getY());
                gc.setStroke(Color.LIMEGREEN);
                gc.stroke();
        };

        EventHandler<MouseEvent> handlerMouseReleased = event -> {
            gc.clearRect(0, 0, CLEAR_RECTANGLE_WIDTH, CLEAR_RECTANGLE_HEIGHT);
            gc.restore();
            gc.closePath();
            isBeginPathReady = true;
        };

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);

        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        paneRoot.getChildren().addAll(canvas);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
