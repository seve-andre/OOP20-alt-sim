package alt.sim.controller.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.Seaside;
import alt.sim.view.TransitionTest;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class GameEngineAreaTest implements GameEngine {

    private static final long PERIOD = 400L;

    private Seaside transitionSeaside;
    private TransitionTest transitionRif;
    private SpawnObject spawn;
    private Plane plane;
    private GraphicsContext gcEngine;

    // Sezione Coordinate campionate
    private Path path;
    private List<Point2D> planeCoordinates;
    private List<Plane> planes;
    private PathTransition pathTransition;

    private boolean playedExplosion;
    private boolean engineStart;

    private Rectangle landingBoxLeft;
    private Rectangle landingBoxRight;

    public GameEngineAreaTest(final TransitionTest transitionRif) {
        spawn = new SpawnObjectImpl();
        this.transitionRif = transitionRif;

        // Sezione campionamento e animazione
        this.path = new Path();
        this.planeCoordinates = new ArrayList<Point2D>();
        this.planes = transitionRif.getPlanes();
        this.pathTransition = new PathTransition();

        this.playedExplosion = false;
        this.engineStart = true;
    }

    public GameEngineAreaTest(final Seaside transitionSeaside) {
        spawn = new SpawnObjectImpl();
        this.transitionSeaside = transitionSeaside;

        // Sezione campionamento e animazione
        this.path = new Path();
        this.planeCoordinates = new ArrayList<Point2D>();
        //Sostituito dal Controller Seaside
        //this.planes = transitionRif.getPlanes();
        this.pathTransition = new PathTransition();

        this.playedExplosion = false;
        this.engineStart = true;
    }

    public GameEngineAreaTest() {
        spawn = new SpawnObjectImpl();
        this.engineStart = false;
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

        while (engineStart) {
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

    public void setEngineStart(final boolean engineStart) {
        this.engineStart = engineStart;
    }

    private void checkCollision() {
        //Sostituito dal Boundas di Seaside
        //Bounds boundaryMap = transitionRif.getCanvas().getBoundsInParent();
        Bounds boundaryMap = transitionSeaside.getCanvas().getBoundsInParent();

        List<Plane> planesToRemove = new LinkedList<>();

        for (Plane planeMonitored:planes) {
            Bounds monitoredPlaneBounds = planeMonitored.getImagePlane().getBoundsInParent();

            for (Plane planeSelected:planes) {
                if (playedExplosion) {
                    break;
                }
                // Check collision Plane
                if (monitoredPlaneBounds.intersects(planeSelected.getImagePlane().getBoundsInParent()) && planeMonitored != planeSelected) {
                    startExplosionPlane(planeMonitored);
                    startExplosionPlane(planeSelected);
                    //transitionSeaside.terminateGame();
                    planesToRemove.add(planeMonitored);
                    planesToRemove.add(planeSelected);
                } else if (planeSelected.getState() != State.SPAWNING
                        && (monitoredPlaneBounds.getMinX() < 0
                                || monitoredPlaneBounds.getMaxX() > boundaryMap.getWidth()
                                || monitoredPlaneBounds.getMinY() < 0
                                || monitoredPlaneBounds.getMaxY() > boundaryMap.getHeight())) {
                    System.out.println("FUORI_BORDO");
                    //startExplosionPlane(planeMonitored);
                    //transitionSeaside.terminateGame();
                    //planesToRemove.add(planeMonitored);
                }

                // Check ready for landing Plane
                if (monitoredPlaneBounds.intersects(landingBoxLeft.getBoundsInParent()) || monitoredPlaneBounds.intersects(landingBoxRight.getBoundsInParent())) {
                    planeMonitored.getLandingAnimation().play();
                    planesToRemove.add(planeMonitored);
                }
            }
        }

        transitionSeaside.removePlanes(planesToRemove);
        planes.removeAll(planesToRemove);
    }

    private void startExplosionPlane(final Plane plane) {
        //Sostituito dal Controller Seaside
        //transitionRif.startExplosionToPane(plane.getExplosionAnimation(), plane);
        transitionSeaside.startExplosionToPane(plane.getExplosionAnimation(), plane);
        playedExplosion = true;
    }

    @Override
    public void processInput() {

    }

    @Override
    public void update(final int elapsed) {
        // Implement the Random-Path of Planes in wait
        Bounds boundaryMap = transitionSeaside.getCanvas().getBoundsInParent();
        for (Plane planeWait:planes) {

            // DA CAPIRE COSA FACEVA
            //if (!planeWait.getIsPlaneSelectedForBeenMoved()) {

            if (!planeWait.isFollowingPath() && planeWait.getStatusMovementAnimation().equals("WAITING")) {
                planeWait.loadRandomTransition(boundaryMap.getWidth(), boundaryMap.getHeight());
            }
            //}

            //System.out.println("Plane Random-Status: " + planeWait.getStatusRandomTransition());
        }

        // Controllo ad ogni frame se Plane collide con qualche oggetto
        checkCollision();
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

    //public void setGraphicContext(final GraphicsContext gcEngine) {
    //  this.gcEngine = transitionRif.getCanvas().getGraphicsContext2D();
    //}

    public void setLandingBoxLeft(final Rectangle rectBoxLeft) {
        this.landingBoxLeft = rectBoxLeft;
    }

    public void setLandingBoxRight(final Rectangle rectBoxRight) {
        this.landingBoxRight = rectBoxRight;
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
