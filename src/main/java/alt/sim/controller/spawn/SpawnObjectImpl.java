package alt.sim.controller.spawn;

public class SpawnObjectImpl implements SpawnObject {
    private static final int SPAWN_FREQUENCY = 10000;
    private static final int OBJECT_NUMBER = 20;
    //private boolean spawned;
    private int objects;
    private Thread threadSpawn;
    public SpawnObjectImpl() {
        objects = 0;
    }
    @Override
    public void startSpawn() {
         class SpawnRunner implements Runnable {
            @Override
            public void run() {
                while (objects < OBJECT_NUMBER) {
                    spawnGameObject();
                    try {
                        Thread.sleep(SPAWN_FREQUENCY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
         }
         threadSpawn = new Thread(new SpawnRunner());
         threadSpawn.start();
    }

    @Override
    public void spawnGameObject() {
        objects++;
    }
    @Override
    public boolean checkGameObjects() {
        return false;
    }

}

