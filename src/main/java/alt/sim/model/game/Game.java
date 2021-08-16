package alt.sim.model.game;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.plane.Plane;

public class Game {
    // Section SPAWN
    //------------------------------------------------------------------------
    // Timer che al termine del count, fa spawnare un Plane
    //private Timeline spawnCountDown;
    private List<Plane> planes;
    private List<Plane> planesToRemove;
    private boolean inGame;
    private boolean startEngine;

    public Game() {
        this.inGame = false;
        this.startEngine = false;
        this.planes = new ArrayList<>();
        this.planesToRemove = new ArrayList<>();
    }

    public void startGame() {
        playSpawnTimer();
    }

    //TODO da implementare
    public void playSpawnTimer() {
        // Implementazione Timer per spawn Plane
        //this.spawnCountDown = new Timeline(new KeyFrame(Duration.seconds(10), cycle -> {
        //spawnPlane(numberPlanesToSpawnEachTime);
        //}));

        //spawnCountDown.setCycleCount(Animation.INDEFINITE);
        //spawnCountDown.play();
    }

    // Section PLANES
    //------------------------------------------------------------------------
    public List<Plane> getPlanes() {
        return this.planes;
    }

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

    /*public void removePlanes(){
        this.planes.removeAll(this.planesToRemove);
        this.planesToRemove.clear();
    }*/

    //------------------------------------------------------------------------

    public void setInGame(final boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return this.inGame;
    }

    public void setStartEngine(final boolean startEngine) {
        this.startEngine = startEngine;
    }

    public boolean getStartEngine() {
        return this.startEngine;
    }
}
