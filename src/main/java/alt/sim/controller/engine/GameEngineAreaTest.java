package alt.sim.controller.engine;

import alt.sim.controller.game.GameController;
import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.seaside.Seaside;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameEngineAreaTest implements GameEngine {
    private static final long PERIOD = 400L;

    private Seaside transitionSeaside;
    // PER TEST FUORIBORDO, da decommentare alla fine
    //private CheckCollisionFix transitionFuoriBordo;
    private SpawnObject spawn;

    // Sezione Coordinate campionate
    private List<Plane> planes;
    private List<Plane> planesToRemove;
    private PathTransition pathTransition;
    private Bounds boundaryMap;
    private AbstractAirStrip stripLeft;
    private AbstractAirStrip stripRight;

    private boolean playedExplosion;
    private boolean engineStart;

    private Rectangle landingBoxLeft;
    private Rectangle landingBoxRight;


    private int scoreGame;
    private GameController gamecontroller;
    private Game gameSession;

    public GameEngineAreaTest(final Seaside transitionSeaside, final Game gameSession) {
        this.gameSession = gameSession;
        this.spawn = new SpawnObjectImpl();
        this.transitionSeaside = transitionSeaside;
        this.gamecontroller = new GameController(this.transitionSeaside, this.gameSession);
        this.boundaryMap = transitionSeaside.getCanvas().getBoundsInParent();
        this.planesToRemove = new LinkedList<>();

        // Sezione campionamento e animazione
        this.pathTransition = new PathTransition();
        this.playedExplosion = false;
        this.engineStart = false;
        this.stripLeft = transitionSeaside.getStripLeft();
        this.stripRight = transitionSeaside.getStripRight();
    }

    public GameEngineAreaTest() {
        this.spawn = new SpawnObjectImpl();
        this.engineStart = false;
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
                    System.out.println("GAME_OVER");
                    System.out.printf(
                            "Collision detected: between plane:%d at (%f, %f) and plane:%d at (%f, %f)\n",
                            planeMonitored.hashCode(), monitoredPlaneBounds.getCenterX(), monitoredPlaneBounds.getCenterY(),
                            planeSelected.hashCode(), selectedPlaneBounds.getCenterX(), selectedPlaneBounds.getCenterY()
                    );

                    startExplosionPlane(planeMonitored);
                    startExplosionPlane(planeSelected);

                    //TODO deccomentare.
                    transitionSeaside.terminateGame();
                    planesToRemove.add(planeMonitored);
                    planesToRemove.add(planeSelected);
                    terminateGame(planeMonitored, planeSelected);
                    break;

                    //Replaced with...
                    //planesToRemove.add(planeMonitored);
                    //planesToRemove.add(planeSelected);

                }
            }
        }

        gameSession.updatePlanes();

        if (!gameSession.isInGame()) {
            transitionSeaside.removePlanes(gameSession.getPlanesToRemove());
            gameSession.clearPlanes();
        }

        //Replaced with...
        //planes.removeAll(planesToRemove);
        //planesToRemove.clear();
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
        final int deltaBound = 5; // value of how much range a Plane is outOfBounds

        /*<<<<<<< Updated upstream
        if (selectedPlaneBounds.getCenterX() >= 0 && selectedPlaneBounds.getCenterY() >= 0) {
            if (selectedPlaneBounds.getCenterX() >= 0 && selectedPlaneBounds.getCenterX() <= deltaBound
                    || selectedPlaneBounds.getCenterX() >= Seaside.SCREEN_BOUND.getWidth() - deltaBound && selectedPlaneBounds.getCenterX() < (Seaside.SCREEN_BOUND.getWidth())
                    || selectedPlaneBounds.getCenterY() >= 0 && selectedPlaneBounds.getCenterY() <= deltaBound
                    || selectedPlaneBounds.getCenterY() >= Seaside.SCREEN_BOUND.getHeight() - deltaBound &&  selectedPlaneBounds.getCenterY() < (Seaside.SCREEN_BOUND.getHeight())) {

        =======*/
        /*if (selectedPlaneBounds.getCenterX() > 0 && selectedPlaneBounds.getCenterY() > 0) {
            if (selectedPlaneBounds.getCenterX() > 0 && selectedPlaneBounds.getCenterX() <= deltaBound
                    || selectedPlaneBounds.getCenterX() >= Seaside.getScreenWidth() - deltaBound && selectedPlaneBounds.getCenterX() < (Seaside.getScreenWidth())
                    || selectedPlaneBounds.getCenterY() > 0 && selectedPlaneBounds.getCenterY() <= deltaBound
                    || selectedPlaneBounds.getCenterY() >= Seaside.getScreenHeight() - deltaBound &&  selectedPlaneBounds.getCenterY() < (Seaside.getScreenHeight())) {*/
        if (selectedPlaneBounds.getMinX() < 0
                || selectedPlaneBounds.getMinX() > Seaside.getScreenWidth()
                || selectedPlaneBounds.getMinY() < 0
                || selectedPlaneBounds.getMaxY() > Seaside.getScreenHeight()) {


            System.out.println("FUORI_BORDO");
            System.out.println("COORDINATE BOUNDS " + planeSelected.hashCode() + ": " + planeSelected.getSprite().getBoundsInParent().getCenterX() + " , " + planeSelected.getSprite().getBoundsInParent().getCenterY());
            //System.out.println("selectedPlaneBounds.getMinX() < 0: " + selectedPlaneBounds.getMinX()
            //        + " selectedPlaneBounds.getMaxX() > boundaryMap.getWidth(): " + selectedPlaneBounds.getMaxX() + " | " +  boundaryMap.getWidth()
            //        + " selectedPlaneBounds.getMinY() < 0:  " + selectedPlaneBounds.getMinY()
            //        + " selectedPlaneBounds.getMaxY() > boundaryMap.getHeight()" + selectedPlaneBounds.getMaxY() + " | "  + boundaryMap.getHeight());

                /*startExplosionPlane(planeSelected);

                //TODO da riportare
                gameSession.addPlaneToRemove(planeSelected);
                transitionSeaside.removePlanes(gameSession.getPlanesToRemove());
                gameSession.removePlanes();
                transitionSeaside.terminateGame();
                gameSession.clearPlaneToRemove();*/

            //planesToRemove.add(planeSelected);
            //transitionSeaside.removePlanes(planesToRemove);
            //planes.removeAll(planesToRemove);
            //planesToRemove.clear();
            return true;
        }
        //}

        return false;
    }

    private boolean checkLanding(final Plane planeSelected) {
        return !planeSelected.isLanded() && (stripLeft.acceptPlane(planeSelected) || stripRight.acceptPlane(planeSelected));
    }

    private void startExplosionPlane(final Plane plane) {
        //TO-DO da riportare
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


        /*int gameScore = transitionSeaside.getGameScore();
        if (gameScore >= 500 && gameScore < 2100) {
            transitionSeaside.setNumberPlanesToSpawn(gameScore / 500 + 1);
        }*/

    }

    @Override
    public void render() {
        /* try {
            if (engineStart && planes.get(0) != null) {
                Bounds monitoredPlaneBounds = planes.get(0).getImgViewPlane().getBoundsInParent();

                Rectangle rect = new Rectangle(monitoredPlaneBounds.getMinX(), monitoredPlaneBounds.getMinY(), monitoredPlaneBounds.getWidth(), monitoredPlaneBounds.getHeight());
                rect.setRotate(planes.get(0).getImgViewPlane().getRotate());
                rect.setFill(null);
                rect.setStroke(Color.BLUE);
                rect.setStrokeWidth(2);

         *//*
         * Platform.runLater(() -> transitionFuoriBordo.drawBounds(rect) );
         *//*
            }
        } catch (Exception e) { }*/
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

    public void setEngineStart(final boolean engineStart) {
        this.engineStart = engineStart;
    }

    public void setPlaneToMove(final Plane plane) {
        this.pathTransition.setNode(plane.getSprite());
    }

    public void setPlanes(final List<Plane> planes) {
        this.planes = planes;
    }

    public void setLandingBoxLeft(final Rectangle rectBoxLeft) {
        this.landingBoxLeft = rectBoxLeft;
    }

    public void setLandingBoxRight(final Rectangle rectBoxRight) {
        this.landingBoxRight = rectBoxRight;
    }

    public void setPathTransition(final PathTransition pathTransition) {
        this.pathTransition = pathTransition;
    }

    public void stopPathTransition() {
        this.pathTransition.stop();
    }
}
