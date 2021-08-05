package alt.sim.view;


import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CreateCleanerRectangleTest extends Application {
    private static final double RECTANGLE_WIDTH = 50;
    private static final double RECTANGLE_HEIGHT = 30;

    private Pane paneRoot;
    private GraphicsContext gc;
    private Canvas canvas;

    private List<Point2D> planeCoordinates;

    @Override
    public void start(final Stage stage) throws Exception {
        paneRoot = new Pane();
        paneRoot.resize(800, 800);

        canvas = new Canvas(paneRoot.getWidth(), paneRoot.getHeight());
        gc = canvas.getGraphicsContext2D();
        planeCoordinates = new ArrayList<Point2D>();

        // Area Listeners
        // -----------------------------------------------------------------
        EventHandler<MouseEvent> handlerMouseMoved = event -> {
            gc.moveTo(event.getX(), event.getY());
        };

        EventHandler<MouseEvent> handlerMouseDragged = event -> {
            planeCoordinates.add(new Point2D(event.getX(), event.getY()));
            drawLines(event);
        };

        EventHandler<MouseEvent> handlerMouseReleased = event -> {
            //clearCanvasLines(planeCoordinates);
            //planeCoordinates.clear();

            gc.clearRect(0, 0, 1900, 1900);
            gc.beginPath();
        };

        EventHandler<KeyEvent> handlerKeyPressed = event -> {
            if (event.getCode() == KeyCode.R) {
                restoreLinesRemoved(planeCoordinates);
            }
        };

        paneRoot.getChildren().add(canvas);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handlerMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handlerMouseReleased);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, handlerKeyPressed);
        Scene scene = new Scene(paneRoot, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void restoreLinesRemoved(final List<Point2D> planeCoordinates) {
        gc.moveTo(planeCoordinates.get(0).getX(), planeCoordinates.get(0).getY());

        for (int k = 1; k < planeCoordinates.size(); k++) {
            gc.lineTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY());
        }

        gc.setStroke(Color.RED);
        gc.stroke();
        gc.beginPath();
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

        if (initialPoint.getY() <= finalPoint.getY()) {
            puntoDiPartenzaY = initialPoint.getY();
        }

        System.out.println("intialPoint: " + initialPoint.getX() + " , " + initialPoint.getY());

        /*
         * if (initialPoint.getX() >= finalPoint.getX()) { puntoDiPartenzaX =
         * finalPoint.getX(); }
         *
         * if (initialPoint.getY() > finalPoint.getY()) { puntoDiPartenzaY =
         * finalPoint.getY(); }
         *
         * if (initialPoint.getY() == finalPoint.getY()) { puntoDiPartenzaY =
         * initialPoint.getY(); }
         */

        width = Math.abs(initialPoint.getX() - finalPoint.getX());
        height = Math.abs(initialPoint.getY() - finalPoint.getY());

        if (height <= 5) {
            height = 50;
        }

        if (width <= 5) {
            width = 50;
        }

        //return new Rectangle(puntoDiPartenzaX - (width / 2), puntoDiPartenzaY - (height / 2), width, height);
        return new Rectangle(puntoDiPartenzaX, puntoDiPartenzaY, width, height);
    }

    public void clearCanvasLines(final List<Point2D> coordinatesToClear) {
        Rectangle rectCleaning;
        Rectangle rectCleaningTopRight;
        Rectangle rectCleaningTopLeft;
        Rectangle rectCleaningBottomLeft;

        for (int k = 0; k < coordinatesToClear.size(); k++) {
            try {
                if (coordinatesToClear.get(k + 1) != null) {
                    rectCleaning = createRectangleCleaned(
                            new Point2D(coordinatesToClear.get(k).getX(), coordinatesToClear.get(k).getY()),
                            new Point2D(coordinatesToClear.get(k + 1).getX(), coordinatesToClear.get(k + 1).getY())
                            );

                    rectCleaningTopRight = rectCleaning;
                    rectCleaningTopRight.setY(rectCleaning.getY() - (rectCleaning.getHeight()));

                    rectCleaningTopLeft = rectCleaning;
                    rectCleaningTopLeft.setY(rectCleaning.getY() - (rectCleaning.getHeight()));
                    rectCleaningTopLeft.setX(rectCleaning.getX() - (rectCleaning.getWidth()));

                    rectCleaningBottomLeft = rectCleaning;
                    rectCleaningBottomLeft.setX(rectCleaning.getX() - (rectCleaning.getWidth()));

                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(), rectCleaning.getHeight());

                    /*
                     *
                     */
                    /*
                     * gc.fillRect(rectCleaningTopRight.getX(), rectCleaningTopRight.getY(),
                     * rectCleaningTopRight.getWidth(), rectCleaningTopRight.getHeight());
                     *
                     * gc.fillRect(rectCleaningTopLeft.getX(), rectCleaningTopLeft.getY(),
                     * rectCleaningTopLeft.getWidth(), rectCleaningTopLeft.getHeight());
                     *
                     * gc.fillRect(rectCleaningBottomLeft.getX(), rectCleaningBottomLeft.getY(),
                     * rectCleaningBottomLeft.getWidth(), rectCleaningBottomLeft.getHeight());
                     */


                    //gc.fillRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(),
                    //       rectCleaning.getHeight() - 200);
                    /*
                     * gc.fillRect(rectCleaning.getX() - 100, rectCleaning.getY(),
                     * rectCleaning.getWidth() + 200, rectCleaning.getHeight() + 200);
                     * gc.fillRect(rectCleaning.getX() + 100, rectCleaning.getY(),
                     * rectCleaning.getWidth(), rectCleaning.getHeight() + 200);
                     * gc.fillRect(rectCleaning.getX() - 100, rectCleaning.getY() - 100,
                     * rectCleaning.getWidth(), rectCleaning.getHeight());
                     * gc.fillRect(rectCleaning.getX() + 100, rectCleaning.getY() + 100,
                     * rectCleaning.getWidth(), rectCleaning.getHeight());
                     */
                }
            } catch (Exception e) {
            }
        }

        for (int k = 0; k < coordinatesToClear.size(); k++) {
            try {
                if (coordinatesToClear.get(k + 1) != null) {
                    rectCleaning = createRectangleCleaned(
                            new Point2D(coordinatesToClear.get(k).getX(), coordinatesToClear.get(k).getY()),
                            new Point2D(coordinatesToClear.get(k + 1).getX(), coordinatesToClear.get(k + 1).getY()));

                    gc.clearRect(rectCleaning.getX(), rectCleaning.getY(), rectCleaning.getWidth(),
                            rectCleaning.getHeight() - (rectCleaning.getHeight() / 2));

                }
            } catch (Exception e) {
            }
        }
    }

    public void drawRectangle(final MouseEvent event) {
        gc.setFill(Color.RED);
        gc.fillRect(event.getX(), event.getY(), RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
    }

    public void drawLines(final MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.setStroke(Color.BLACK);
        gc.stroke();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
