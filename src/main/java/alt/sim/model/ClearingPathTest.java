package alt.sim.model;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.plane.Plane;
import alt.sim.view.MainPlaneView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ClearingPathTest extends Application {
    private Pane paneRoot;

    private Plane plane1;
    private Plane plane2;
    private List<Point2D> planeCoordinates;

    private GraphicsContext gc;
    private Canvas canvas;

    private boolean premutoUnaVolta = false;

    @Override
    public void start(final Stage stage) throws Exception {

        planeCoordinates = new ArrayList<>();

        paneRoot = new Pane();
        plane1 = new Plane("images/map_components/airplane.png");
        plane2 = new Plane("images/map_components/airplane.png");

        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        paneRoot.getChildren().add(plane1.getImagePlane());
        paneRoot.getChildren().add(plane2.getImagePlane());

        plane2.getImagePlane().setX(500);
        plane2.getImagePlane().setY(500);

        canvas = new Canvas(paneRoot.getWidth(), paneRoot.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.lineTo(100, 600);
        gc.lineTo(100, 800);
        gc.stroke();

        // Area Listeners
        //-----------------------------------------------------------------
        EventHandler<MouseEvent> handlerMouseDragged = event -> {
             planeCoordinates.add(new Point2D(event.getX(), event.getY()));

             // Disegno linea su Canvas
             gc.lineTo(event.getX(), event.getY());
             gc.setStroke(Color.BLUE);
             gc.stroke();
        };

        EventHandler<MouseEvent> handlerMouseReleased = event -> {
            if (premutoUnaVolta) {
                clearCanvasLines();
                premutoUnaVolta = false;
            }

            planeCoordinates.clear();
            gc.beginPath();
            premutoUnaVolta = true;
        };

        EventHandler<MouseEvent> handlerMouseMoved = event -> {
            gc.moveTo(event.getX(), event.getY());
        };

        EventHandler<MouseEvent> handlerMouseClicked = event -> {
            //Rectangle rect = createRectangleCleaned(new Point2D(event.getX(), event.getY()), new Point2D(event.getX() + 10, event.getY() + 10));
            //gc.fillRect(event.getX(), event.getY(), 30, 15);
            //gc.clearRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        };

        //-------------------------------------------------

        // Implementazione ripulitura Linee sul Canvas
        paneRoot.getChildren().add(canvas);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerMouseClicked);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
    }

    public void clearCanvasLines() {
        Rectangle rectCleaning;

        for (int k = 0; k < planeCoordinates.size(); k++) {
            try {
                if (planeCoordinates.get(k + 1) != null) {
                    rectCleaning = createRectangleCleaned(new Point2D(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()), new Point2D(planeCoordinates.get(k + 1).getX(), planeCoordinates.get(k + 1).getY()));
                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(), rectCleaning.getHeight());
                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(), rectCleaning.getHeight() - 500);
                    gc.clearRect(rectCleaning.getX() - 100, rectCleaning.getY(), rectCleaning.getWidth() + 200, rectCleaning.getHeight() + 200);
                    gc.clearRect(rectCleaning.getX() + 100, rectCleaning.getY(), rectCleaning.getWidth() + 200, rectCleaning.getHeight() + 200);
                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY() - 100, rectCleaning.getWidth(), rectCleaning.getHeight());
                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY() + 100, rectCleaning.getWidth(), rectCleaning.getHeight());

                }
            } catch (Exception e) {}
        }

        for (int k = 0; k < planeCoordinates.size(); k++) {
            try {
                if (planeCoordinates.get(k + 1) != null) {
            rectCleaning = createRectangleCleaned(new Point2D(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()), new Point2D(planeCoordinates.get(k + 1).getX(), planeCoordinates.get(k + 1).getY()));
            gc.clearRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(), rectCleaning.getHeight() - (rectCleaning.getHeight() / 2));
                }
            } catch (Exception e) {}
        }
    }

    public Rectangle createRectangleCleaned(final Point2D initialPoint, final Point2D finalPoint) {
        // Punto dove posizionare il rettangolo che pulirà lo schermo
        double puntoDiPartenzaX = 0;
        double puntoDiPartenzaY = 0;
        // larghezza e altezza del rettangolo che pulirà lo schermo
        double width;
        double height;

        if (initialPoint.getX() <= finalPoint.getX()) {
            puntoDiPartenzaX = initialPoint.getX();
        }

        if (initialPoint.getX() >= finalPoint.getX()) {
            puntoDiPartenzaX = finalPoint.getX();
        }

        if (initialPoint.getY() > finalPoint.getY()) {
            puntoDiPartenzaY = finalPoint.getY();
        }

        if (initialPoint.getY() <= finalPoint.getY()) {
            puntoDiPartenzaY = initialPoint.getY();
        }

        if (initialPoint.getY() == finalPoint.getY()) {
            puntoDiPartenzaY = initialPoint.getY();
        }

        width = Math.abs(initialPoint.getX() - finalPoint.getX());
        height = Math.abs(initialPoint.getY() - finalPoint.getY());

        if (height <= 5) {
            height = 50;
        }

        if (width <= 5) {
            //width = 50;
        }


        return new Rectangle(puntoDiPartenzaX, puntoDiPartenzaY, width, height);
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
