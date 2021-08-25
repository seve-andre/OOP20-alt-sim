package alt.sim.model.game;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.plane.PlaneImpl;

public class GameImpl implements Game {
    // Section Spawn Plane
    private static final int MAX_PLANE_TO_SPAWN = 10;
    private static final int SPAWN_DELAY = 10;
    private static final int GAME_SCORE_LANDING = 100;
    private static final int COORDINATES_LIMIT = 200;
    private static final int MIN_COORDINATES_LENGTH = 5;
    private static final int MAX_DISTANCE_DRAW_PATH_VALUE = 150;

    private int numberPlanesToSpawnEachTime;
    private List<PlaneImpl> planes;
    private List<PlaneImpl> planesToRemove;
    private boolean inGame;

    public GameImpl() {
        this.inGame = false;
        this.planes = new ArrayList<>();
        this.planesToRemove = new ArrayList<>();
        this.numberPlanesToSpawnEachTime = 1;
    }

    // Section PLANES
    //------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public List<PlaneImpl> getPlanes() {
        return this.planes;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlaneImpl> getPlanesToRemove() {
        return this.planesToRemove;
    }

    /**
     * {@inheritDoc}
     */
    public void addPlane(final PlaneImpl plane) {
        this.planes.add(plane);
    }

    /**
     * {@inheritDoc}
     */
    public void addPlaneToRemove(final PlaneImpl planeToRemove) {
        this.planesToRemove.add(planeToRemove);
    }

    /**
     * {@inheritDoc}
     */
    public void clearPlanes() {
        this.planes.clear();
        this.planesToRemove.clear();
    }

    /**
     * {@inheritDoc}
     */
    public void updatePlanes() {
        planes.removeAll(planesToRemove);
    }

    /**
     * {@inheritDoc}
     */
    public void removePlanes() {
        this.planes.removeAll(this.planesToRemove);
        this.planesToRemove.clear();
    }

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
     */
    public int getNumberPlanesToSpawnEachTime() {
        return numberPlanesToSpawnEachTime;
    }

    /**
     * {@inheritDoc}
     */
    public void setNumberPlanesToSpawnEachTime(final int numberPlanesToSpawnEachTime) {
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

    /**
     * @return the game score to update when a Plane is land.
     */
    public static int getGameScoreLanding() {
        return GAME_SCORE_LANDING;
    }

    public static int getMinCoordinatesLength() {
        return MIN_COORDINATES_LENGTH;
    }

    public static int getMaxDistanceDrawPathValue() {
        return MAX_DISTANCE_DRAW_PATH_VALUE;
    }

    public static int getCoordinatesLimit() {
        return COORDINATES_LIMIT;
    }
}
