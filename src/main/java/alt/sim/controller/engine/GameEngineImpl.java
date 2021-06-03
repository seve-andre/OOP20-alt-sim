package alt.sim.controller.engine;

import java.util.Iterator;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.view.PlaneMouseMove;
import javafx.geometry.Point2D;

public class GameEngineImpl implements GameEngine {

    private static final long PERIOD = 1000L;
    private SpawnObject spawn;
    private PlaneMouseMove plane;
    private Iterator<Point2D> iterator;
    private Point2D[] vet;
    private int cont;

    public GameEngineImpl(final PlaneMouseMove plane) {
        spawn = new SpawnObjectImpl();
        this.plane = plane;
        this.vet = this.plane.getPlaneMovement().getPlaneCoordinates();
        cont = 0;
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
        // TODO Auto-generated method stub

    }

    @Override
    public void update(final int elapsed) {
        if (cont < this.plane.getPlaneMovement().getCoordinatesLimit()) {
            this.plane.getPlane().getSpritePlane().setX(this.vet[cont].getX());
            this.plane.getPlane().getSpritePlane().setY(this.vet[cont].getY());
            cont++;
        }
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

    }
}
