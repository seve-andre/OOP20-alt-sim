package alt.sim.controller.engine;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.view.PlaneMouseMove;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameEngineImpl implements GameEngine {

    private static final long PERIOD = 2000L;
    private SpawnObject spawn;
    private PlaneMouseMove plane;
    private Point2D[] vet;
    private int cont;
    private boolean start;
    private Path path = new Path();
    private PathTransition pathTransition =  new PathTransition();

    public GameEngineImpl(final PlaneMouseMove plane) {
        spawn = new SpawnObjectImpl();
        this.plane = plane;
        cont = 0;
        start = false;
        path.getElements().add(new MoveTo(0, 0));
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
            this.vet = this.plane.getPlaneMovement().getPlaneCoordinates();
            System.out.println("Stampa: " + this.vet[cont]);
            pathTransition.setNode(plane.getPlane().getImagePlane());
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            for (int i = 0; i < this.plane.getPlaneMovement().getPlaneCoordinates().length; i++) {
                path.getElements().add(new LineTo(this.vet[i].getX(), this.vet[i].getY()));
            }
            pathTransition.setDuration(Duration.millis(10000));
            pathTransition.setPath(path);
            pathTransition.play();
            start = false;
            //path = new Path();
        }
    }

    @Override
    public void update(final int elapsed) {
        //System.out.println("Print in update: " + this.vet[0]);
        /*if (start) {
            if (cont < this.plane.getPlaneMovement().getPlaneCoordinates().length) { //da modificare il controllo
                // sugli elementi del vettore
                this.plane.getPlane().getImagePlane().setLayoutX(this.vet[cont].getX());
                this.plane.getPlane().getImagePlane().setLayoutY(this.vet[cont].getY());
                cont++;
                //System.out.println(this.plane.getPlaneMovement().getCoordinatesLimit());
            } else {
                start = false;
                cont = 0;
            }
        }*/
    }

    @Override
    public void render() {

    }

    public void setStart(final boolean start) {
        this.start = start;
    }


    /*Path path = new Path();
     path.getElements().add (new MoveTo (0f, 50f));
     path.getElements().add (new CubicCurveTo (40f, 10f, 390f, 240f, 1904, 50f));

     pathTransition.setDuration(Duration.millis(10000));
     pathTransition.setNode(rect);
     pathTransition.setPath(path);
     pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
     pathTransition.setCycleCount(4f);
     pathTransition.setAutoReverse(true);

     pathTransition.play();*/
}
