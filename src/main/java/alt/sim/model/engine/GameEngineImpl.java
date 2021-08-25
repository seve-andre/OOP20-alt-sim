package alt.sim.model.engine;

import java.util.Collections;

import alt.sim.Main;
import alt.sim.controller.airstrip.AirStripController;
import alt.sim.controller.game.GameControllerImpl;
import alt.sim.controller.seaside.SeasideController;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.game.GameImpl;
import alt.sim.model.plane.PlaneImpl;
import alt.sim.model.plane.State;
import javafx.geometry.Bounds;

public final class GameEngineImpl implements GameEngine {
    private static final long PERIOD = 400L;

    private SeasideController transitionSeaside;

    // Sampled Coordinates Section
    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;

    private boolean playedExplosion;
    private GameControllerImpl gamecontroller;
    private GameImpl gameSession;
    private static GameEngine instance = null;

    public GameEngineImpl(final SeasideController transitionSeaside, final GameImpl gameSession) {
        this.gameSession = gameSession;
        this.transitionSeaside = transitionSeaside;
        this.gamecontroller = new GameControllerImpl(this.transitionSeaside, this.gameSession);

        //Animation and Sampling section
        this.playedExplosion = false;
        this.stripLeft = transitionSeaside.getStripLeft();
        this.stripRight = transitionSeaside.getStripRight();
    }

    /**
     * {@inheritDoc}
     */
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
     * Check all the collision in Game (collision with the Airstrip, collision with borderMap, collision between Planes).
     */
    private void checkCollision() {
        for (PlaneImpl planeMonitored : gameSession.getPlanes()) {
            Bounds monitoredPlaneBounds = planeMonitored.getSprite().getBoundsInParent();

            if (checkLanding(planeMonitored)) {
                transitionSeaside.addScore(GameImpl.getGameScoreLanding());
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

            for (PlaneImpl planeSelected : gameSession.getPlanes()) {
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
     * @param more  list of Plane to terminate.
     */
    private void terminateGame(final PlaneImpl first, final PlaneImpl... more) {
        gameSession.addPlaneToRemove(first);
        Collections.addAll(gameSession.getPlanesToRemove(), more);
        transitionSeaside.terminateGame();
        terminateInstance();
    }

    /**
     * Check if planeMonitored goes out of bounds.
     *
     * @param planeSelected is the Plane select for check
     * @return true if the collision is verified, false otherwise
     */
    private synchronized boolean checkOutOfBounds(final PlaneImpl planeSelected) {
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
    private boolean checkLanding(final PlaneImpl planeSelected) {
        return !planeSelected.isLanded() && (AirStripController.acceptPlane(stripLeft, transitionSeaside, planeSelected)
                || AirStripController.acceptPlane(stripRight, transitionSeaside, planeSelected));
    }

    /**
     * @param plane the Plane selected where execute the ExplosionAnimation.
     */
    private void startExplosionPlane(final PlaneImpl plane) {
        transitionSeaside.startExplosionPane(plane.getExplosionAnimation(), plane);
        playedExplosion = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processInput() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final int elapsed) {
        // checkCollision effectuated every frame period
        checkCollision();
        this.gamecontroller.checkScore(transitionSeaside.getIntScore());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * @param transitionSeaside field to pass at the constructor of GameEngineImpl class
     * @param gameSession       field to pass at the constructor of GameEngineImpl class
     * @return a single instance of the actual GameEngineImpl class, implemented the Singleton pattern.
     */
    public static synchronized GameEngine getInstance(final SeasideController transitionSeaside, final GameImpl gameSession) {
        if (instance == null) {
            instance = new GameEngineImpl(transitionSeaside, gameSession);
        }

        return instance;
    }

    /**
     * terminate the instance field when the Game is over.
     */
    public static void terminateInstance() {
        instance = null;
    }
}
