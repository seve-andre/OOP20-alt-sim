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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameEngineAreaTest implements GameEngine {

    private static final long PERIOD = 400L;

    private TransitionTest transitionRif;
    private SpawnObject spawn;
    private Plane plane;
    private GraphicsContext gcEngine;

    // Sezione Coordinate campionate
    private Path path;
    private List<Point2D> planeCoordinates;
    private List<Plane> planes;
    private PathTransition pathTransition;

    private boolean fatto;

    public GameEngineAreaTest(final TransitionTest transitionRif) {
       this.transitionRif = transitionRif;

       // Sezione campionamento e animazione
       this.path = new Path();
       this.planeCoordinates = new ArrayList<Point2D>();
       this.planes = transitionRif.getPlanes();
       this.pathTransition = new PathTransition();

       this.fatto = false;
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

    private void checkCollision() {
        for (int k = 0; k < planes.size(); k++) {

            Plane planeMonitored = planes.get(k);

            for (Plane planeSelected:planes) {
                if (planeMonitored.getImagePlane().getBoundsInParent().intersects(planeSelected.getImagePlane().getBoundsInParent()) && planeMonitored.hashCode() != planeSelected.hashCode()) {
                    if (!fatto) {
                        transitionRif.startExplosionToPane(planeMonitored.getKeyFrameTest());
                        fatto = true;
                    }
                }
            }
        }
    }

    @Override
    public void processInput() {
        // Implement the Random-Path of Planes in wait
        for (Plane planeWait:planes) {
            //if (!planeWait.getIsPlaneSelectedForBeenMoved()) {

            if (!planeWait.isFollowingPath() && planeWait.getStatusMovementAnimation() == "WAITING") {
                planeWait.loadRandomTransition();
            }

            //System.out.println("Plane Random-Status: " + planeWait.getStatusRandomTransition());
        }

        // Controllo ad ogni frame se Plane collide con qualche oggetto
        checkCollision();
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

    public void setPlane(final Plane plane) {
        this.plane = plane;
    }

    /*
     * public void connectPlaneToPathTransition(final Plane plane) { double
     * pathLenght = 0; double velocityMovement = 0.005; double duration = 0;
     * 
     * PathTransition pathTransitionPlane = new PathTransition();
     * copyCoordinatesInPath(planeCoordinates); pathTransitionPlane.setPath(path);
     * pathTransitionPlane.setNode(this.plane.getImagePlane());
     * pathTransitionPlane.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
     * 
     * //-------------------------------------------
     * setCoordinates(planeCoordinates); setPathTransition(pathTransition);
     * 
     * // Cambiare la velocità a seconda del percoso: pathLenght =
     * planeCoordinates.size(); duration = pathLenght / velocityMovement;
     * pathTransitionPlane.setDuration(Duration.millis(duration)); //
     * -------------------------------------------
     * 
     * pathTransitionPlane.play(); pathTransitionPlane.setOnFinished(event -> {
     * //transitionRif.clearPlaneCoordinatesAndUpdate(plane.getId()); });
     * 
     * //plane.setIsPlaneSelectedForBeenMoved(false); setReadyToStart(true); }
     */

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

    public void setGraphicContext(final GraphicsContext gcEngine) {
        this.gcEngine = transitionRif.getCanvas().getGraphicsContext2D();
    }

    public void setPlaneToMove(final Plane plane) {
        this.pathTransition.setNode(plane.getImagePlane());
    }

    public void setPlanes(final List<Plane> planes) {
        this.planes = planes;
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
