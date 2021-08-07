package alt.sim.controller.game;

import alt.sim.model.plane.Plane;
import alt.sim.view.seaside.Seaside;

import java.util.List;

public final class GameController {

    private static final int LIMIT_500 = 500;
    private static final int LIMIT_1000 = 1000;
    private static final int LIMIT_1500 = 1500;
    private static final int LIMIT_2000 = 2000;
    private static final int LIMIT_2100 = 2100;
    private Seaside transitionSeaside;

    public GameController(final Seaside transitionSeaside) {
        this.transitionSeaside = transitionSeaside;
    }

//<<<< Updated upstream
    private void pauseResumeOrStop(final boolean pause, final boolean resume, final boolean stop) {
        List<Plane> planes = transitionSeaside.getPlanes();

//==== TODO QUALI Conflicts ELIMINARE ???
    //private static void pauseResumeOrStop(final boolean pause, final boolean resume, final boolean stop) {
        /*List<Plane> planes = Seaside.getPlanes();
>>>> Stashed change

        planes.forEach(plane -> {
            if (pause) {
                if (plane.getPlaneMovementAnimation() != null) {
                    plane.getPlaneMovementAnimation().pause();
                }
                if (plane.getLandingAnimation() != null) {
                    plane.getLandingAnimation().pause();
                }
                if (plane.getRandomTransition() != null) {
                    plane.getRandomTransition().pause();
                }
                transitionSeaside.getSpawnCountDown().pause();
            } else if (resume) {
                if (plane.getPlaneMovementAnimation() != null && plane.getStatusMovementAnimation().equals("PAUSED")) {
                    plane.getPlaneMovementAnimation().play();
                }
                if (plane.getLandingAnimation() != null && plane.getStatusMovementAnimation().equals("PAUSED")) {
                    plane.getLandingAnimation().play();
                }
                if (plane.getRandomTransition() != null && plane.getStatusMovementAnimation().equals("PAUSED")) {
                    plane.getRandomTransition().play();
                }
                transitionSeaside.getSpawnCountDown().play();
            } else if (stop) {
                if (plane.getPlaneMovementAnimation() != null) {
                    plane.getPlaneMovementAnimation().stop();
                }
                if (plane.getLandingAnimation() != null) {
                    plane.getLandingAnimation().stop();
                }
                if (plane.getRandomTransition() != null) {
                    plane.getRandomTransition().stop();
                }
                transitionSeaside.getSpawnCountDown().stop();
            }
        });*/
    }

    public void pause() {
        pauseResumeOrStop(true, false, false);
    }

    public void resume() {
        pauseResumeOrStop(false, true, false);
    }

    public void stop() {
        pauseResumeOrStop(false, false, true);
    }

    public void checkScore(final int score) {
         if (score < LIMIT_2100) {
            if (score >= LIMIT_500 && score < LIMIT_1000) {
                transitionSeaside.setNumberPlanesToSpawn(2);
            } else if (score >= LIMIT_1000 && score <= LIMIT_1500) {
                transitionSeaside.setNumberPlanesToSpawn(3);
            } else if (score >= LIMIT_2000) {
                transitionSeaside.setNumberPlanesToSpawn(4);
            }
        }
    }
}
