package alt.sim.controller.game;

import alt.sim.model.plane.Plane;
import alt.sim.view.seaside.Seaside;

import java.util.List;

public final class GameController {

    private Seaside transitionSeaside;

    public GameController(final Seaside transitionSeaside) {
        this.transitionSeaside = transitionSeaside;
    }

    private static void pauseResumeOrStop(final boolean pause, final boolean resume, final boolean stop) {
        List<Plane> planes = Seaside.getPlanes();
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
            }
        });
    }

    public static void pause() {
        pauseResumeOrStop(true, false, false);
    }

    public static void resume() {
        pauseResumeOrStop(false, true, false);
    }

    public static void stop() {
        pauseResumeOrStop(false, false, true);
    }

    public void checkScore(final int score) {
         if (score < 2100) {
            if (score >= 500 && score < 1000) {
                transitionSeaside.setNumberPlanesToSpawn(2);
            } else if (score >= 1000 && score <= 1500) {
                transitionSeaside.setNumberPlanesToSpawn(3);
            } else if (score >= 2000) {
                transitionSeaside.setNumberPlanesToSpawn(4);
            }
        }
    }
}
