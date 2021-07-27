package alt.sim.model.spawn;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.map.MapImpl;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import javafx.animation.PathTransition;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public final class SpawnModel {

    private static final int PATH_TRANSITION_DURATION = 5000;
    private static final int X = 80;
    private static final int Y = 80;

    private static final MapImpl MAP = new MapImpl();

    private static final int HEIGHT = MAP.getHeight();
    private static final int WIDTH = MAP.getWidth();
    private static final int HEIGHT_2 = HEIGHT / 2;
    private static final int WIDTH_2 = WIDTH / 2;

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
                path.getElements().add(new CubicCurveTo(180, 0, 200, 120, 250, HEIGHT_2));
                break;
            case TOP:
                path.getElements().add(new MoveTo(WIDTH_2, Y));
                path.getElements().add(new LineTo(WIDTH_2, 200));
                break;
            case RIGHT:
                path.getElements().add(new MoveTo(WIDTH + X, HEIGHT_2));
                path.getElements().add(new LineTo(X, HEIGHT_2));
                break;
            case BOTTOM:
                path.getElements().add(new MoveTo(WIDTH_2, HEIGHT + Y));
                path.getElements().add(new LineTo(WIDTH_2, Y));
                break;
            default:
                break;
        }

        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setNode(plane.getImagePlane());
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
            plane.getImagePlane().setFitWidth(64);
            plane.getImagePlane().setFitHeight(64);
            planes.add(plane);
        }

        return planes;
    }
}
