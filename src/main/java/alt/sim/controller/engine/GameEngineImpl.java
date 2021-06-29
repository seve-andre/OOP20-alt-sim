package alt.sim.controller.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.view.PlaneMouseMove;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameEngineImpl implements GameEngine {

    private static final long PERIOD = 400L;
    private SpawnObject spawn;
    private PlaneMouseMove plane;
    private List<Point2D> vet;
    // private Point2D[] vet;
    //private Point2D[] coordinatesTest;

    private PathTransition pathTransition;
    private Path path = new Path();

    private int cont;
    private boolean start;
    private boolean blocked = false;
    private boolean animationIsRunning;

    public GameEngineImpl(final PlaneMouseMove plane) {
        this.pathTransition = new PathTransition();
        spawn = new SpawnObjectImpl();
        this.plane = plane;

        start = false;
        this.cont = 0;

        animationIsRunning = false;
        this.blocked = false;

        path.getElements().add(new MoveTo(0, 0));
        //this.vet = this.plane.getPlaneMovement().getPlaneCoordinatesList();

        this.vet = new ArrayList<>();

        pathTransition.setNode(plane.getPlane().getImagePlane());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setDuration(Duration.millis(PERIOD));
    }

    @Override
    public void initGame() {
        spawn.startSpawn();
    }

    @Override
    public void mainLoop() throws IllegalArgumentException {
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
    public void processInput() {
        if (start) {
            /*
             * for (int i = 0; i < vet.length; i++) { path.getElements().add(new
             * LineTo(vet[i].getX(), vet[i].getY())); }
             */
        }

    }

    @Override
    public void update(final int elapsed) {
    }

    @Override
    public void render() {
        if (start) {
            start = false;
            //plane.getPlaneMovement().printPlaneCoordinates();
            //this.vet = this.plane.getPlaneMovement().getPlaneCoordinatesList();

            double x = getLineTo(cont).getX();
            double y = getLineTo(cont).getY();

            /*
             * for (Point2D point:this.vet) { System.out.println("vet int GameEngineImpl: "
             * + point); }
             */

            Path path = new Path();
            ListIterator<Point2D> iterator = vet.listIterator();
            path.getElements().add(new MoveTo(vet.get(cont).getX(), vet.get(cont).getY()));
            //path.getElements().add(new MoveTo(0, 0));

            try {

                if (blocked) {
                    pathTransition = new PathTransition();
                }

                if (iterator.hasNext() && !blocked) {

                    if (cont < vet.size() && (cont + 1) < vet.size()) {
                        //path.getElements().add(new MoveTo(vet.get(cont).getX(), vet.get(cont).getY()));
                        path.getElements().add(getLineTo(cont + 1));
                        System.out.println("Vet value " + vet.get(cont).getX() + " , " + vet.get(cont).getY());

                        cont++;
                        pathTransition.setPath(path);
                        pathTransition.play();

                        pathTransition.setOnFinished(finisch -> this.plane.getPlane().getImagePlane().setLayoutX(x));
                        pathTransition.setOnFinished(finisch -> this.plane.getPlane().getImagePlane().setLayoutY(y));
                        pathTransition.setOnFinished(finisch -> this.start = true);
                    } else {
                        pathTransition.stop();
                        pathTransition.setOnFinished(finisch -> this.plane.getPlane().getImagePlane().setLayoutX(x));
                        pathTransition.setOnFinished(finisch -> this.plane.getPlane().getImagePlane().setLayoutY(y));
                        pathTransition.setOnFinished(finisch -> this.start = false);
                    }
                } else {
                    pathTransition.stop();
                    pathTransition = new PathTransition();
                    path = new Path();
                    start = false;
                }
            }

            catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }

    public LineTo getLineTo(final int cont) {
        System.out.println("cont " + cont);
        System.out.println("Vet value " + vet.get(cont).getX() + " , " + vet.get(cont).getY());

        return new LineTo(this.vet.get(cont).getX(), this.vet.get(cont).getY());
    }

    public void setStart(final boolean start) {
        this.start = start;
    }

    private void checkAnimationStatus() {
        animationIsRunning = pathTransition.getStatus() == Status.RUNNING;
    }

    public boolean getAnimationStatus() {
        checkAnimationStatus();

        return this.animationIsRunning;
    }

    /*
     * public void setStartFinischedAnimation() { if (blocked == false) { this.start
     * = true; } else { this.start = false; } }
     */


    public void setBlocked(final boolean isBlocked) { 
        this.blocked = isBlocked; 
    }

    public void setCoordinate(final List<Point2D> vet) {
        this.vet = vet;

        //Reinizializzazione delle animazioni:
        path = new Path();
        //pathTransition = new PathTransition();
        blocked = false;
        start = true;

        /*
         * for (Point2D point:this.vet) {
         * System.out.println("setCoordinate in GameEngineImpl: " + point); }
         */
    }


    public void stopAnimation() {
        //blockAnimation();
        this.pathTransition.stop();
    }

    public PathTransition getPathTransition() {
        return this.pathTransition;
    }
}
