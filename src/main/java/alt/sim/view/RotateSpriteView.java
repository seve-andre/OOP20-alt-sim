package alt.sim.view;

import alt.sim.model.PlaneMovement;
import alt.sim.model.SpriteType;
import alt.sim.model.plane.Plane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Controlling the Position of the Plane in the Map using MouseEvent controls.
 *
 */
public class RotateSpriteView extends Application {

    private PlaneMovement planeMove;
    private Plane p1;
    private double xCached;
    private double yCached;

    public RotateSpriteView() {
        p1 = new Plane(SpriteType.AIRPLANE);
        planeMove = new PlaneMovement();
    }

    @Override
    public void start(final Stage stage) throws Exception {

        Pane paneRoot = new Pane();
        Canvas canvas = new Canvas(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Calculating the Proportion --> (Image:Screen)
        p1.getSpritePlane().getImageSpriteResized().resizeImageSprite(true);

        // View Plane demonstrating:
        paneRoot.resize(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Insert Plane test into view:
        paneRoot.getChildren().add(p1.getSpritePlane().getImageSpriteResized().getImageSprite());

        // Section Canvas
        paneRoot.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        xCached = (p1.getImagePlane().getLayoutX() + p1.getImagePlane().getFitWidth());
        yCached = (p1.getImagePlane().getLayoutY() + p1.getImagePlane().getFitHeight() / 2);

        //check Rotate Method
        //calulateAngularCoefficent(p1);

        EventHandler<MouseEvent> handlerMouseClicked = new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {
                Point2D coordinatesCopy = calulateAngularCoefficent(p1, event, xCached, yCached);
                drawShapes(gc, event.getX(), event.getY());
                //centerImagePositionInGame(p1, event);

                gc.strokeLine(xCached, yCached, event.getX(), event.getY());

                //centerImagePositionInGame(p1, event);
                xCached = coordinatesCopy.getX();
                yCached = coordinatesCopy.getY();
                System.out.println("boundsInParent: " + p1.getImagePlane().getBoundsInParent().getMaxX() + " , " + p1.getImagePlane().getBoundsInParent().getMaxY());

                System.out.println("xCached && yCached: " + xCached + " , " + yCached);
            }
        };

        EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent event) {
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerMouseClicked);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);

        Scene scene = new Scene(paneRoot, MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());
        stage.setScene(scene);
        stage.show();
    }

    private Point2D calulateAngularCoefficent(final Plane planeInGame, final MouseEvent event, final double xCached, final double yCached) {
        double tanAlfa;
        double alfa;

        tanAlfa = (event.getY() - yCached) / (event.getX() - xCached);
        alfa = Math.atan(tanAlfa);
        alfa = Math.toDegrees(alfa);

        System.out.println("alfa: " + alfa);
        planeInGame.getImagePlane().setRotate(alfa);

        return (planeTip(planeInGame, event));
    }

    private void centerImagePositionInGame(final Plane planeInGame, final MouseEvent event) {
        //Insert Center Image when click

        // To set the tip of Plane perfectly
        //planeInGame.getImagePlane().setLayoutX(event.getX() - 113));
        planeInGame.getImagePlane().setLayoutX(event.getX() - (planeInGame.getImagePlane().getFitWidth() / 2));
        planeInGame.getImagePlane().setLayoutY(event.getY() - (planeInGame.getImagePlane().getFitHeight() / 2));

    }

    private Point2D planeTip(final Plane planeInGame, final MouseEvent event) {
        double planeTipX;
        double planeTipY;

        //planeTipX = (event.getX() - 113);
        //planeTipY = (event.getY() - planeInGame.getImagePlane().getFitHeight() / 2);

        planeTipX = planeInGame.getImagePlane().getBoundsInParent().getMinX() + planeInGame.getImagePlane().getFitWidth();
        planeTipY = planeInGame.getImagePlane().getBoundsInParent().getMinY() + planeInGame.getImagePlane().getFitHeight() / 2;

        System.out.println("bound value: " + planeTipX + " , " + planeTipY);

        return new Point2D(planeTipX, planeTipY);
    }

    private void drawShapes(final GraphicsContext gc, final double x, final double y) {
        gc.strokeOval(x, y, 3, 3);
        gc.stroke();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}

