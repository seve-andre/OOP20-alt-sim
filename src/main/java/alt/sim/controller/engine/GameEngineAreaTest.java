package alt.sim.controller.engine;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;

public class GameEngineAreaTest implements GameEngine {

    private static final long PERIOD = 400L;

    private SpawnObject spawn;

    
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

    @Override
    public void processInput() {

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

}
