package alt.sim.model.game;

import alt.sim.model.plane.PlaneImpl;

import java.util.List;

public interface Game {

    /**
     * @return list of planes in game GETTER.
     */
    List<PlaneImpl> getPlanes();

    /**
     * @return list of planesToRemove GETTER.
     */
    List<PlaneImpl> getPlanesToRemove();

    /**
     * @param plane inserted into the plane List.
     */
    void addPlane(final PlaneImpl plane);

    /**
     * @param planeToRemove inserted into the planeToRemove List.
     */
    void addPlaneToRemove(final PlaneImpl planeToRemove);

    /**
     * Clear both the plane List(planes && planeToRemove)
     */
    void clearPlanes();

    /**
     * Removing all the planesTo remove from planes List.
     */
    void updatePlanes();

    /**
     * Removing all the planesTo remove from planes List and clear the planesToRemove List.
     */
    void removePlanes();

    /**
     * @param inGame setted for choose if the Game is started or not.
     */
    void setInGame(final boolean inGame);

    /**
     * @return number of Plane to spawn.
     */
    int getNumberPlanesToSpawnEachTime();

    /**
     *
     * @param numberPlanesToSpawnEachTime Define the number of Plane to spawn.
     */
    void setNumberPlanesToSpawnEachTime(final int numberPlanesToSpawnEachTime);
}
