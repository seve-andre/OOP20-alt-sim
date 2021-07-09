package alt.sim.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * View class for detected the Rectangle collision.
 */
public class CollisionDetectionView extends Application {
        private static final int WALL_X = 150;
        private static final int CIRCLE_RADIUS = 40;
        private static final int MOVED_HEIGHT = 50;
        private static final int MOVED_WIDTH = 50;
        private static final int WALL_HEIGHT = 100;
        private static final int WALL_WIDTH = 50;
        /** Screen width of the view, calling Screen class that return the size of the Primary screen.  */
        private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
        /** Screen height of the view.  */
        private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

        private static boolean fullScreenMode;
        private Scene scene;
        private Pane paneRoot;

        public CollisionDetectionView() {
            this.paneRoot = new Pane();
            this.paneRoot.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
            this.scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        public CollisionDetectionView(final double screenWidth, final double screenHeight) {
            this.paneRoot = new Pane();
            this.paneRoot.resize(screenWidth, screenHeight);
            this.scene = new Scene(paneRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        @Override
        public void start(final Stage stage) throws Exception {

            try {
                //ImageView airStripeSprite = new ImageView(new Image(ImageClassification.AIRSTRIP.getURLImage(), 128.0, 128.0, true, false));
                //ImageView planeSprite = new ImageView(new Image(ImageClassification.AIRPLANE.getURLImage(), 128.0, 128.0, true, false));

                Rectangle rectangleWall = new Rectangle(WALL_WIDTH, WALL_HEIGHT);
                Rectangle rectangleMoved = new Rectangle(MOVED_WIDTH, MOVED_HEIGHT);
                Circle circleMoved = new Circle(CIRCLE_RADIUS);

                rectangleMoved.setFill(Paint.valueOf("BLUE"));
                rectangleWall.setLayoutX(WALL_X);
                rectangleWall.setLayoutY(100);
                rectangleWall.setFill(Paint.valueOf("RED"));

                //Mouse Click event
                EventHandler<MouseEvent> eventMouseClicked = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent e) {
                        //rectangleMoved.setLayoutX(e.getX());
                        //rectangleMoved.setLayoutY(e.getY());

                        circleMoved.setCenterX(e.getX());
                        circleMoved.setCenterY(e.getY());

                       //Check collison Plane with AirStrip

                       if (rectangleWall.getBoundsInParent().intersects(rectangleMoved.getBoundsInParent())) {
                           System.out.println("AirStrip COLLIDED!!!");
                       }

                       if (rectangleWall.getBoundsInParent().intersects(circleMoved.getBoundsInParent())) {
                            System.out.println("AirStrip COLLIDED!!!");
                       }
                    }
                };

                //Registering the event filter
                //paneRoot.addEventFilter(MouseEvent.MOUSE_CLICKED, eventMouseClicked);

                ImageView imageTestSelected = new ImageView(new Image("images/map_components/airplaneSelected.png"));
                imageTestSelected.setLayoutX(500);
                imageTestSelected.setLayoutY(500);

                paneRoot.getChildren().addAll(rectangleWall, rectangleMoved, circleMoved, imageTestSelected);
                stage.setScene(scene);
                stage.show();

            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Launch a standalone application calling the JavaFx mainThread.
         * @param args the command line arguments.
         */
        public static void main(final String[] args) {
            launch(args);
        }

        public static double getScreenWidth() {
            return SCREEN_WIDTH;
        }

        public static double getScreenHeight() {
            return SCREEN_HEIGHT;
        }

        public static void setFullScreenTrue() {
            fullScreenMode = true;
        }

        public static void setFullScreenFalse() {
            fullScreenMode = false;
        }

        public static boolean isFullScreen() {
            return fullScreenMode;
        }

        public Pane getPane() {
            return this.paneRoot;
        }

        public Scene getScene() {
            return this.scene;
        }
}

