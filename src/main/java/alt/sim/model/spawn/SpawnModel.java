package alt.sim.model.spawn;

import alt.sim.model.map.MapImpl;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class SpawnModel {

    private final PathTransition pathTransition = new PathTransition();
    private final Path path = new Path();
    private static final MapImpl MAP = new MapImpl();

    private static final int HEIGHT = MAP.getHeight();
    private static final int WIDTH = MAP.getWidth();

    private static final int HEIGHT_2 = HEIGHT / 2;
    private static final int WIDTH_2 = WIDTH / 2;
    private static final int X = 80;
    private static final int Y = 80;


    public void spawn(final ImageView plane) {

        for (int i = 1; i <= 4; i++) {

            switch (i) {
                case 1:
                    this.path.getElements().add(new MoveTo(-X, HEIGHT_2));
                    this.path.getElements().add(new LineTo(WIDTH, HEIGHT_2));
                    break;
                case 2:
                    this.path.getElements().add(new MoveTo(WIDTH_2, -Y));
                    this.path.getElements().add(new LineTo(WIDTH_2, HEIGHT));
                    break;
                case 3:
                    this.path.getElements().add(new MoveTo(WIDTH + X, HEIGHT_2));
                    this.path.getElements().add(new LineTo(0, HEIGHT_2));
                    break;
                case 4:
                    this.path.getElements().add(new MoveTo(WIDTH_2, HEIGHT + Y));
                    this.path.getElements().add(new LineTo(WIDTH_2, 0));
                    break;
                default:
                    break;
            }
        }

        this.pathTransition.setPath(this.path);
        this.pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        this.pathTransition.setNode(plane);
        this.pathTransition.setDuration(Duration.millis(3000));
        this.pathTransition.play();
    }

}
