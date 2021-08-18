package alt.sim.controller.engine;

import alt.sim.Main;
import alt.sim.controller.game.GameController;
import java.util.Collections;

import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.seaside.Seaside;
import javafx.geometry.Bounds;

public class GameEngineImpl implements GameEngine {
    private static final long PERIOD = 400L;

    private Seaside transitionSeaside;

    // Sampled Coordinates Section
    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;

    private boolean playedExplosion;
    private GameController gamecontroller;
    private Game gameSession;

    public GameEngineImpl(final Seaside transitionSeaside, final Game gameSession) {
        this.gameSession = gameSession;
        this.transitionSeaside = transitionSeaside;
        this.gamecontroller = new GameController(this.transitionSeaside, this.gameSession);

        //Animation and Sampling section
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

    /**
     * Check all the collision in Game (collision with the Airstrip, collision with borderMap, collision between Planes)
     */
    private void checkCollision() {
        for (Plane planeMonitored : gameSession.getPlanes()) {
            Bounds monitoredPlaneBounds = planeMonitored.getSprite().getBoundsInParent();

            if (checkLanding(planeMonitored)) {
                transitionSeaside.addScore(gameSession.getGameScoreLanding());
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

    /**
     * @param first first Plane passed to terminate.
     * @param more list of Plane to terminate.
     */
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

        return selectedPlaneBounds.getMinX() < 0
                || selectedPlaneBounds.getMaxX() > Main.getStage().getWidth()
                || selectedPlaneBounds.getMinY() < 0
                || selectedPlaneBounds.getMaxY() > Main.getStage().getHeight();
    }

    /**
     * @param planeSelected the Plane passed to the method for check if is in the Landing Area to land
     * @return a boolean value of is ready to land or not.
     */
    private boolean checkLanding(final Plane planeSelected) {
        return !planeSelected.isLanded() && (stripLeft.acceptPlane(planeSelected) || stripRight.acceptPlane(planeSelected));
    }

    /**
     *
     * @param plane the Plane selected where execute the ExplosionAnimation.
     */
    private void startExplosionPlane(final Plane plane) {
        transitionSeaside.startExplosionToPane(plane.getExplosionAnimation(), plane);
        playedExplosion = true;
    }

    @Override
    public void processInput() {
    }

    /**
     * @param elapsed time of every frames update.
     */
    @Override
    public void update(final int elapsed) {
        // checkCollision effectuated every frame period
        checkCollision();
        this.gamecontroller.checkScore(transitionSeaside.getIntScore());
    }

    @Override
    public void render() {
    }

    @Override
    public void initGame() {
    }

    /**
     * Calculates how many milliseconds has to wait for next frame.
     *
     * @param current time of wait for the next frame.
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
