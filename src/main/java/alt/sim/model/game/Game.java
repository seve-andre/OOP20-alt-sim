package alt.sim.model.game;

import alt.sim.controller.engine.GameEngineAreaTest;
import alt.sim.model.plane.Plane;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private  List<Plane> planes;
    private GameEngineAreaTest engine;

    // Implementazione Timer per spawn Plane
    private Timer spawnTimer;
    private TimerTask spawnTask;
    private TimerTask task;


    private boolean endGame;

    public Game(){
        this.endGame = false;
        this.planes = new ArrayList();
    }

    public void startGame() {
        playSpawnTimer();
    }

    public void playSpawnTimer(){
        this.spawnTimer = new Timer();
        this.spawnTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Spawn Plane");

            }
        };

        spawnTimer.schedule(spawnTask,5000l);
    }


    public void setEndGame(final boolean endGame){
        this.endGame = endGame;
    }

    public boolean getEndGame(){
        return this.endGame;
    }

}
