package alt.sim.controller.engine;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.view.PlaneMouseMove;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;

public class GameEngineImpl implements GameEngine {

    private static final long PERIOD = 400L;
    private SpawnObject spawn;
    private PlaneMouseMove plane;
    private Point2D[] vet;
    private boolean start;
    private int cont;
    private Path path = new Path();

    public GameEngineImpl(final PlaneMouseMove plane) {
        spawn = new SpawnObjectImpl();
        this.plane = plane;
        start = false;
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
        if (start) {
            this.vet = this.plane.getPlaneMovement().getPlaneCoordinates();
            /*for (int i = 0; i < vet.length; i++) {
                path.getElements().add(new LineTo(vet[i].getX(), vet[i].getY()));
            }*/
        }
    }

    @Override
    public void update(final int elapsed) {
        if (start) {
            if (cont < this.vet.length) {
                plane.startTransiction(vet[cont].getX(), vet[cont].getY());
                cont++;
            }
        }
    }

    @Override
    public void render() {

    }

    public void setStart(final boolean start) {
        this.start = start;
    }
}
