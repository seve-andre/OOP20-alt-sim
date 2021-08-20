package alt.sim.model.plane;

import javafx.animation.PathTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class UserTransitionTest {
   /* private PathTransition transition;
    private PlaneTest planeUsed;
    private Path path;

    public UserTransitionTest() {
        this.transition = new PathTransition();
    }

    public UserTransitionTest(final PlaneTest planeUsed) {
        this();
        this.planeUsed = planeUsed;
    }

    public void loadUserTransition() {
        double pathLenght;
        final double velocityMovement = 0.03;
        double duration;

        //if (planeUsed.getState() == State.SPAWNING) {
          //  return;
        //}

        //if (planeUsed.getRandomTransition().getStatus() == Animation.Status.RUNNING) {
          //  planeUsed.getRandomTransition().stop();
        //}

        // update the coordinate befor call this method
        copyCoordinatesInPath();

       *//* if (this.transition.getStatus() == Animation.Status.RUNNING) {
            this.transition.stop();
            planeUsed.getPlaneLinesPath().clear();
            //TODO da capire come implementare
            //controllerSeaside.clearLinesDrawed();
            //controllerSeaside.restoreLinesRemoved();
        }*//*

        this.transition.setPath(path);
        this.transition.setNode(planeUsed.getSprite());
        this.transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        // Stablished a standard velocity depended on Pathlenght.
        pathLenght = (planeUsed.getPlaneLinesPath().get(0).distance(planeUsed.getPlaneLinesPath().get(planeUsed.getPlaneLinesPath().size() - 1)));
        duration = pathLenght / velocityMovement;
        this.transition.setDuration(Duration.millis(duration));

        planeUsed.setState(State.USER_MOVEMENT);
        this.transition.play();

        this.transition.setOnFinished(event -> {
            planeUsed.getPlaneLinesPath().clear();
            //TODO da capire come implementare
            //controllerSeaside.clearLinesDrawed();
            //controllerSeaside.restoreLinesRemoved();
            planeUsed.setState(State.WAITING);
        });
    }

    private void copyCoordinatesInPath() {
        // Cleaning the coordinates presented
        this.path = new Path();

        for (int k = 0; k < planeUsed.getPlaneLinesPath().size(); k++) {

            if (planeUsed.getPlaneLinesPath().get(k).getX() > 0 && planeUsed.getPlaneLinesPath().get(k).getY() > 0) {
                if (k == 0) {
                    path.getElements().add(new MoveTo(planeUsed.getPlaneLinesPath().get(k).getX(), planeUsed.getPlaneLinesPath().get(k).getY()));
                } else {
                    path.getElements().add(new LineTo(planeUsed.getPlaneLinesPath().get(k).getX(), planeUsed.getPlaneLinesPath().get(k).getY()));
                }
            }
        }
    }

    public PlaneTest getPlaneUsed() {
        return planeUsed;
    }

    public void setPlaneUsed(final PlaneTest planeUsed) {
        this.planeUsed = planeUsed;
    }*/
}
