package alt.sim.controller.spawn;

/**
 * This interface manage methods for the spawn timer and game objects to spawn.
 */
public interface SpawnObject {
    /**
     * Start thread that spawns game objects and manages time..
     */
    void startSpawn();
    
    /**
     * Create the new GameObject to spawn.
     */
    void spawnGameObject();
    
    /**
     * Check if all objects are spawned.
     * @return true if all objects are spawned.
     */
    boolean checkGameObjects();
}
