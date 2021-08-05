package alt.sim.controller.engine;

import java.util.LinkedList;
import java.util.List;

import alt.sim.controller.spawn.SpawnObject;
import alt.sim.controller.spawn.SpawnObjectImpl;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.seaside.Seaside;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;

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

    private int scoreGame = 1500;
    //private Model model;

    public GameEngineAreaTest(final Seaside transitionSeaside) {
        this.spawn = new SpawnObjectImpl();
        this.transitionSeaside = transitionSeaside;
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

        while (engineStart) {
            long current = System.currentTimeMillis();
            int elapsed = (int) (current - lastTime);

            processInput();
            update(elapsed);
            render();

            try {
                waitForNextFrame(current);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lastTime = current;
        }
    }

    private void checkCollision() {
        for (Plane planeMonitored:planes) {
            Bounds monitoredPlaneBounds = planeMonitored.getSprite().getBoundsInParent();

            if (checkLanding(planeMonitored)) {
                //scoreGame += 100;
                planesToRemove.add(planeMonitored);
                continue;
            }

            if (planeMonitored.getState() == State.SPAWNING) {
                continue;
            }

            if (checkOutOfBounds(planeMonitored)) {
                break;
            }

            for (Plane planeSelected:planes) {
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
                    transitionSeaside.terminateGame();
                    //planesToRemove.add(planeMonitored);
                    //planesToRemove.add(planeSelected);
                }
            }
        }

        transitionSeaside.removePlanes(planesToRemove);
        planes.removeAll(planesToRemove);
        planesToRemove.clear();
    }

    /**
     * Check if planeMonitored goes out of bounds.
     * @param planeSelected is the Plane select for check
     * @return true if the collision is verified, false otherwise
     */
    private synchronized boolean checkOutOfBounds(final Plane planeSelected) {
        Bounds selectedPlaneBounds = planeSelected.getSprite().getBoundsInParent();
        final int deltaBound = 5; // value of how much range a Plane is outOfBounds

        if (selectedPlaneBounds.getCenterX() >= 0 && selectedPlaneBounds.getCenterY() >= 0) {
            if (selectedPlaneBounds.getCenterX() >= 0 && selectedPlaneBounds.getCenterX() <= deltaBound
                    || selectedPlaneBounds.getCenterX() >= Seaside.SCREEN_BOUND.getWidth() - deltaBound && selectedPlaneBounds.getCenterX() < (Seaside.SCREEN_BOUND.getWidth())
                    || selectedPlaneBounds.getCenterY() >= 0 && selectedPlaneBounds.getCenterY() <= deltaBound
                    || selectedPlaneBounds.getCenterY() >= Seaside.SCREEN_BOUND.getHeight() - deltaBound &&  selectedPlaneBounds.getCenterY() < (Seaside.SCREEN_BOUND.getHeight())) {

                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("FUORI_BORDO");
                    alert.show();
                });

                System.out.println("COORDINATE ERROR " + planeSelected.hashCode() + ": " + planeSelected.getSprite().getBoundsInParent().getCenterX() + " , " + planeSelected.getSprite().getBoundsInParent().getCenterY());
                //System.out.println("selectedPlaneBounds.getMinX() < 0: " + selectedPlaneBounds.getMinX()
                //        + " selectedPlaneBounds.getMaxX() > boundaryMap.getWidth(): " + selectedPlaneBounds.getMaxX() + " | " +  boundaryMap.getWidth()
                //        + " selectedPlaneBounds.getMinY() < 0:  " + selectedPlaneBounds.getMinY()
                //        + " selectedPlaneBounds.getMaxY() > boundaryMap.getHeight()" + selectedPlaneBounds.getMaxY() + " | "  + boundaryMap.getHeight());

                startExplosionPlane(planeSelected);
                //TO-DO da riportare
                planesToRemove.add(planeSelected);
                transitionSeaside.removePlanes(planesToRemove);
                planes.removeAll(planesToRemove);
                planesToRemove.clear();
                transitionSeaside.terminateGame();
                return true;
            }
        }

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
    public void processInput() { }

    @Override
    public void update(final int elapsed) {
        // Controllo ad ogni frame se Plane collide con qualche oggetto
        checkCollision();

        /* if (scoreGame < 2100) {
            if (scoreGame >= 500 && scoreGame <= 1000) {
                transitionSeaside.setNumberPlanesToSpawn(2);
            } else if (scoreGame >= 1000 && scoreGame <= 1500) {
                transitionSeaside.setNumberPlanesToSpawn(3);
            } else if (scoreGame >= 2000) {
                transitionSeaside.setNumberPlanesToSpawn(4);
            }
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
    protected void waitForNextFrame(final long current) throws InterruptedException, IllegalArgumentException {
        long dt = System.currentTimeMillis() - current;

        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
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
