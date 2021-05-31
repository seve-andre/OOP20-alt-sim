package alt.sim.controller.spawn;

import java.util.Random;

import alt.sim.model.container.PlaneContainer;
import alt.sim.model.container.PlaneContainerImpl;
import alt.sim.model.plane.Plane;

public class SpawnObjectImpl implements SpawnObject {
    private static final int SPAWN_FREQUENCY = 10000;
    private static final int OBJECT_NUMBER = 20;
    //private boolean spawned;
    private int objects;
    private Thread threadSpawn;
    private PlaneContainer container;
    public SpawnObjectImpl() {
        objects = 0;
        container = new PlaneContainerImpl();
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
        container.addPlane(new Plane(SpawnObjectImpl.PlaneChoice.getPlaneType()));
        objects++;
    }
    @Override
    public boolean checkGameObjects() {
        return false;
    }

    private static class PlaneChoice {
        private static final int VALUES = 2;
        private static final int FIRST = 0;
        private static Random rnd = new Random();

        public static String getPlaneType() {
            if (rnd.nextInt(VALUES) == FIRST) {
                return "images/map_components/airplane.png";
            } else {
                return "images/map_components/helicopter.png";
            }
        }
    }

}

