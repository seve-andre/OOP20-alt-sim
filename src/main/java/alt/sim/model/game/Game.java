package alt.sim.model.game;

import alt.sim.model.plane.Plane;

import java.util.ArrayList;
import java.util.List;

public class Game {
    // Section Spawn Plane
    private static final int MAX_PLANE_TO_SPAWN = 10;
    private static final int SPAWN_DELAY = 10;

    private int numberPlanesToSpawnEachTime;
    private List<Plane> planes;
    private List<Plane> planesToRemove;
    private boolean inGame;

    public Game() {
        this.inGame = false;
        this.planes = new ArrayList<>();
        this.planesToRemove = new ArrayList<>();
    }

    // Section PLANES
    //------------------------------------------------------------------------

    /**
     * @return list of planes in game GETTER.
     */
    public List<Plane> getPlanes() {
        return this.planes;
    }

    /**
     * @return list of planesToRemove GETTER.
     */
    public List<Plane> getPlanesToRemove() {
        return this.planesToRemove;
    }

    public void addPlane(final Plane plane) {
        this.planes.add(plane);
    }

    public void addPlaneToRemove(final Plane planeToRemove) {
        this.planesToRemove.add(planeToRemove);
    }

    public void clearPlanes() {
        this.planes.clear();
        this.planesToRemove.clear();
    }

    public void updatePlanes() {
        planes.removeAll(planesToRemove);
    }

    public void removePlanes(){
        this.planes.removeAll(this.planesToRemove);
        this.planesToRemove.clear();
    }

    //------------------------------------------------------------------------


    public void setInGame(final boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * @return value of state of Game.
     */
    public boolean isInGame() {
        return this.inGame;
    }

    /**
     * @return number of Plane to spawn.
     */
    public int getNumberPlanesToSpawnEachTime() {
        return numberPlanesToSpawnEachTime;
    }

    /**
     * define the number of Plane to spawn.
     */
    public void setNumberPlanesToSpawnEachTime(int numberPlanesToSpawnEachTime) {
        this.numberPlanesToSpawnEachTime = numberPlanesToSpawnEachTime;
    }

    /**
     * @return MAX number of Plane to spawn in Game.
     */
    public static int getMaxPlaneToSpawn() {
        return MAX_PLANE_TO_SPAWN;
    }

    /**
     * @return the creation of Plane delay.
     */
    public static int getSpawnDelay() {
        return SPAWN_DELAY;
    }
}
