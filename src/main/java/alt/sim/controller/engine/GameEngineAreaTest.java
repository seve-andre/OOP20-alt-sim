package alt.sim.controller.engine;

import java.util.ArrayList;
import java.util.List;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.model.plane.Plane;
import alt.sim.view.TransitionTest;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameEngineAreaTest implements GameEngine {

    private static final long PERIOD = 400L;

    private TransitionTest transitionRif;
    private SpawnObject spawn;

    // Sezione Coordinate campionate
    private Path path;
    private List<Point2D> planeCoordinates;
    private PathTransition pathTransition;

    private boolean readyToStart;

    public GameEngineAreaTest(final TransitionTest transitionRif) {
       this.transitionRif = transitionRif;

       // Sezione campionamento e animazione
       this.path = new Path();
       this.planeCoordinates = new ArrayList<Point2D>();
       this.pathTransition = new PathTransition();

       this.readyToStart = false;
    }

    public GameEngineAreaTest() {
        spawn = new SpawnObjectImpl();
    }

    /**
     * Calculates how many milliseconds has to wait for next frame.
     * 
     * @param current
     * @throws InterruptedException
     * @throws IllegalArgumentException
     */
    protected void waitForNextFrame(final long current) throws InterruptedException, IllegalArgumentException {
        long dt = System.currentTimeMillis() - current;

        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mainLoop() {
        long lastTime = System.currentTimeMillis();

        while (true) {
            long current = System.currentTimeMillis();
            int elapsed = (int) (current - lastTime);

            processInput();
            update(elapsed);
            render();

            try {
                waitForNextFrame(current);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lastTime = current;
        }
    }

    private boolean checkCollision() {
        return transitionRif.getRectangleWall().getBoundsInParent().intersects(transitionRif.getPlaneSprite().getBoundsInParent());
    }

    @Override
    public void processInput() {
        /*
         * if (readyToStart) { readyToStart = false;
         * 
         * pathTransition.play(); }
         */

        // Controllo ad ogni frame se Plane collide con qualche oggetto
        if (checkCollision()) {
            System.out.println("COLLIDED!!!");
        }
    }

    @Override
    public void update(final int elapsed) {

    }

    @Override
    public void render() {

    }

    @Override
    public void initGame() {
        spawn.startSpawn();
    }

    public void connectPlaneToPathTransition(final Plane plane) {
        int pathLenght = 0;

        PathTransition pathTransitionPlane = new PathTransition();
        copyCoordinatesInPath(planeCoordinates);
        pathTransitionPlane.setPath(path);
        pathTransitionPlane.setNode(plane.getImagePlane());
        pathTransitionPlane.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        // -------------------------------------------
        // Cambiare la velocità a seconda del percoso:
        pathLenght = planeCoordinates.size();
        System.out.println("pathLenght = " + pathLenght);

        if (pathLenght <= 10) {
            pathTransitionPlane.setDuration(Duration.seconds(1.5));
        } else if (pathLenght > 10 && pathLenght <= 50) {
            pathTransitionPlane.setDuration(Duration.seconds(5));
        } else if (pathLenght > 50 && pathLenght <= 100){
            pathTransitionPlane.setDuration(Duration.seconds(10));
        } else if (pathLenght > 100 && pathLenght <= 150){
            pathTransitionPlane.setDuration(Duration.seconds(15));
        } else if (pathLenght > 150 && pathLenght <= 200){
            pathTransitionPlane.setDuration(Duration.seconds(20));
        }
        // -------------------------------------------

        pathTransitionPlane.play();

        setCoordinates(planeCoordinates);
        setPathTransition(pathTransition);
        setReadyToStart(true);
    }

    private void copyCoordinatesInPath(final List<Point2D> planeCoordinates) {
        // Ripuliamo le coordinate presenti dal path prima
        path = new Path();

        for (int k = 0; k < planeCoordinates.size(); k++) {

            if (k == 0) {
                path.getElements().add(new MoveTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()));
            } else {
                path.getElements().add(new LineTo(planeCoordinates.get(k).getX(), planeCoordinates.get(k).getY()));
            }
        }
    }

    public void setPlaneToMove(final Plane plane) {
        this.pathTransition.setNode(plane.getImagePlane());
    }

    public void setReadyToStart(final boolean readyToStart) {
        this.readyToStart = readyToStart;
    }

    public void setCoordinates(final List<Point2D> planeCoordinates) {
        this.planeCoordinates = planeCoordinates;
    }

    public void setPathTransition(final PathTransition pathTransition) {
        this.pathTransition = pathTransition;
    }

    public void stopPathTransition() {
        this.pathTransition.stop();
    }

}
