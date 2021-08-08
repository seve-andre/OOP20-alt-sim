package alt.sim.model.spawn;

import alt.sim.Main;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class SpawnModel {

    private static final int PATH_TRANSITION_DURATION = 5000;
    private static final int X = 50;
    private static final int Y = 50;

    private static final double HEIGHT = Main.getStage().getHeight();
    private static final double WIDTH = Main.getStage().getWidth();
    private static final double HEIGHT_2 = HEIGHT / 2;
    private static final double WIDTH_2 = WIDTH / 2;
    private static ImageView indicatorImage = new ImageView(new Image("images/animations/indicator.png"));

    private SpawnModel() { }

    /**
     * Creates PathTransition based on spawn location.
     * @param plane to spawn
     * @param spawnLocation where to spawn
     * @return PathTransition from spawn location
     */
    public static PathTransition spawn(final Plane plane, final SpawnLocation spawnLocation) {

        Path path = new Path();
        PathTransition pathTransition = new PathTransition();

        switch (spawnLocation) {
            case LEFT:
                path.getElements().add(new MoveTo(-X, HEIGHT_2));
                path.getElements().add(new LineTo(X, HEIGHT_2));
                loadIndicator(0, HEIGHT_2);
                break;
            case TOP:
                path.getElements().add(new MoveTo(WIDTH_2, -Y));
                path.getElements().add(new LineTo(WIDTH_2, Y));
                loadIndicator(WIDTH_2, 0);
                break;
            case RIGHT:
                path.getElements().add(new MoveTo(WIDTH + X, HEIGHT_2));
                path.getElements().add(new LineTo(WIDTH - X, HEIGHT_2));
                loadIndicator(WIDTH, HEIGHT_2);
                break;
            case BOTTOM:
                path.getElements().add(new MoveTo(WIDTH_2, HEIGHT + Y));
                path.getElements().add(new LineTo(WIDTH_2, HEIGHT - Y));
                loadIndicator(WIDTH_2, HEIGHT);
                break;
            default:
                break;
        }

        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setNode(plane.getSprite());
        pathTransition.setDuration(Duration.millis(PATH_TRANSITION_DURATION));
        pathTransition.setOnFinished(event -> plane.setState(State.WAITING));

        return pathTransition;
    }

    /**
     * Generates list of 4 different planes.
     * @return list containing planes
     */
    public static List<Plane> generatePlanes() {

        List<Plane> planes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Plane plane = new Plane("images/map_components/airplane.png");
            plane.setState(State.SPAWNING);
            plane.getSprite().setFitWidth(64);
            plane.getSprite().setFitHeight(64);
            planes.add(plane);
        }

        return planes;
    }

    /**
     * Generates list of 4 different indicators.
     * @return list containing indicators
     */
    public static List<ImageView> generateIndicators() {

        List<ImageView> indicators = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ImageView indicatorImage = new ImageView(new Image("images/animations/indicator.png"));
            indicators.add(indicatorImage);
        }

        return indicators;
    }

    public static void loadIndicator(final double x, final double y) {

        Timeline indicatorTimer = new Timeline(new KeyFrame(Duration.seconds(1), cycle -> {
            FadeTransition indicatorTransition = new FadeTransition();
            indicatorTransition.setNode(indicatorImage);
            indicatorTransition.setDuration(Duration.seconds(1));
            indicatorTransition.setFromValue(1);
            indicatorTransition.setToValue(0);
            indicatorTransition.play();
        }));

        indicatorImage.setX(x);
        indicatorImage.setY(y);
        indicatorTimer.setCycleCount(2);
        indicatorTimer.play();
    }
}
