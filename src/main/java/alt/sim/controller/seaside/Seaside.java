package alt.sim.controller.seaside;

import alt.sim.model.plane.PlaneImpl;
import alt.sim.model.spawn.SpawnLocation;

import java.util.Collection;

public interface Seaside {

    /**
     * @param numberPlaneSpawn passed the number of Plane to spawn, there are created and spawned calling the Plane method playSpawnTransition().
     */
    void spawnPlane(int numberPlaneSpawn);

    /**
     * @param side selected a side where collocated the indicator, there image are created and setted into the Map with FadeTransition animation.
     */
    void loadIndicatorAnimation(SpawnLocation side);

    /**
     * routine of end Game, that launch the Page.GAMEOVER view and terminate alla the animation on running.
     */
    void terminateGame();

    /**
     * re-initialize the GraphicsContext, clear the linesDrawned and  restore the lines that not be eliminated.
     */
    void clearMap();

    /**
     * restore the Plane value isPlaneSelectedForBeenMoved.
     */
    void clearPlaneSelectedForBeenMoved();

    /**
     * clear all the lines drawed in the Map.
     */
    void clearLinesDrawed();

    /**
     * @param planes removes all the Planes in Game after the Game is over.
     */
    void removePlanes(Collection<? extends PlaneImpl> planes);

    /**
     * @param plane removes a single Planes in Game after Plane is landed.
     */
    void removePlane(PlaneImpl plane);

    /**
     * @param score set the Game Score into the Label.
     */
    void addScore(int score);

    /**
     * @return checked if there are more than one Plane selected for choose after the last Plane selected between the two.
     */
    boolean isMoreThanOneSelected();
}
