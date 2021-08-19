package alt.sim.view.engine;

import alt.sim.Main;
import alt.sim.controller.game.GameController;
import alt.sim.controller.seaside.SeasideController;

import java.util.Collections;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.engine.GameEngine;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import javafx.geometry.Bounds;

public class GameEngineAreaTest implements GameEngine {
    private static final long PERIOD = 400L;

    private SeasideController transitionSeaside;
    // PER TEST FUORIBORDO, da decommentare alla fine
    private SpawnObject spawn;

    // Sezione Coordinate campionate
    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;

    private boolean playedExplosion;
    private GameController gamecontroller;
    private Game gameSession;

    public GameEngineAreaTest(final SeasideController transitionSeaside, final Game gameSession) {
        this.gameSession = gameSession;
        this.spawn = new SpawnObjectImpl();
        this.transitionSeaside = transitionSeaside;
        this.gamecontroller = new GameController(this.transitionSeaside, this.gameSession);

        // Sezione campionamento e animazione
        this.playedExplosion = false;
        this.stripLeft = transitionSeaside.getStripLeft();
        this.stripRight = transitionSeaside.getStripRight();
    }

    @Override
    public void mainLoop() {
        long lastTime = System.currentTimeMillis();

        while (gameSession.isInGame()) {
            long current = System.currentTimeMillis();
            int elapsed = (int) (current - lastTime);

            processInput();
            update(elapsed);
            render();

            try {
                waitForNextFrame(current);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            lastTime = current;
        }
    }

    private void checkCollision() {
        for (Plane planeMonitored : gameSession.getPlanes()) {
            Bounds monitoredPlaneBounds = planeMonitored.getSprite().getBoundsInParent();

            if (checkLanding(planeMonitored)) {
                transitionSeaside.addScore(100);
                gameSession.addPlaneToRemove(planeMonitored);
                continue;
            }

            if (planeMonitored.getState() == State.SPAWNING) {
                continue;
            }

            if (checkOutOfBounds(planeMonitored)) {
                startExplosionPlane(planeMonitored);
                terminateGame(planeMonitored);
                break;
            }

            for (Plane planeSelected : gameSession.getPlanes()) {
                if (playedExplosion) {
                    break;
                }

                if (planeSelected == planeMonitored || planeSelected.isLanded() || planeSelected.getState() == State.SPAWNING) {
                    continue;
                }

                Bounds selectedPlaneBounds = planeSelected.getSprite().getBoundsInParent();

                // Check collision Plane
                if (monitoredPlaneBounds.intersects(selectedPlaneBounds)) {
                    startExplosionPlane(planeMonitored);
                    startExplosionPlane(planeSelected);
                    terminateGame(planeMonitored, planeSelected);
                    break;
                }
            }
        }

        gameSession.updatePlanes();

        if (!gameSession.isInGame()) {
            transitionSeaside.removePlanes(gameSession.getPlanesToRemove());
            gameSession.clearPlanes();
        }
    }

    private void terminateGame(final Plane first, final Plane... more) {
        gameSession.addPlaneToRemove(first);
        Collections.addAll(gameSession.getPlanesToRemove(), more);
        transitionSeaside.terminateGame();
    }

    /**
     * Check if planeMonitored goes out of bounds.
     *
     * @param planeSelected is the Plane select for check
     * @return true if the collision is verified, false otherwise
     */
    private synchronized boolean checkOutOfBounds(final Plane planeSelected) {
        Bounds selectedPlaneBounds = planeSelected.getSprite().getBoundsInParent();

        if (selectedPlaneBounds.getMinX() < 0
                || selectedPlaneBounds.getMaxX() > Main.getStage().getWidth()
                || selectedPlaneBounds.getMinY() < 0
                || selectedPlaneBounds.getMaxY() > Main.getStage().getHeight()) {
            return true;
        }
        return false;
    }

    private boolean checkLanding(final Plane planeSelected) {
        return !planeSelected.isLanded() && (stripLeft.acceptPlane(planeSelected) || stripRight.acceptPlane(planeSelected));
    }

    private void startExplosionPlane(final Plane plane) {
        transitionSeaside.startExplosionToPane(plane.getExplosionAnimation(), plane);
        playedExplosion = true;
    }

    @Override
    public void processInput() {
    }

    @Override
    public void update(final int elapsed) {
        // Controllo ad ogni frame se Plane collide con qualche oggetto
        checkCollision();
        this.gamecontroller.checkScore(transitionSeaside.getIntScore());
    }

    @Override
    public void render() {
    }

    @Override
    public void initGame() {
        spawn.startSpawn();
    }

    /**
     * Calculates how many milliseconds has to wait for next frame.
     *
     * @param current
     * @throws InterruptedException
     * @throws IllegalArgumentException
     */
    protected void waitForNextFrame(final long current) {
        long dt = System.currentTimeMillis() - current;

        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (IllegalArgumentException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
