package alt.sim.model.game;

import alt.sim.model.plane.Plane;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private boolean endGame;

    public Game() {
        this.endGame = false;
        List<Plane> planes = new ArrayList<>();
    }

    public void startGame() {
        playSpawnTimer();
    }

    public void playSpawnTimer() {
        // Implementazione Timer per spawn Plane
        Timer spawnTimer = new Timer();
        //System.out.println("Spawn Plane");
        TimerTask spawnTask = new TimerTask() {

            @Override
            public void run() {
                //System.out.println("Spawn Plane");
            }
        };

        spawnTimer.schedule(spawnTask, 5000L);
    }


    public void setEndGame(final boolean endGame) {
        this.endGame = endGame;
    }

    public boolean getEndGame() {
        return this.endGame;
    }

}
