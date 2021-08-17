package alt.sim.controller.game;

import java.util.List;

import alt.sim.model.game.Game;
import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.seaside.Seaside;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;

public final class GameController {

    private static final int LIMIT_500 = 500;
    private static final int LIMIT_1000 = 1000;
    private static final int LIMIT_1500 = 1500;
    private static final int LIMIT_2000 = 2000;
    private static final int LIMIT_2100 = 2100;

    private static Seaside transitionSeaside = null;
    private static Game gameModel = null;

    public GameController(final Seaside seaside, final Game game) {
        if (transitionSeaside == null) {
            transitionSeaside = seaside;
            gameModel = game;
        }
    }

        private static void pauseResumeOrStop(final boolean pause, final boolean resume, final boolean stop) {
        List<Plane> planes = gameModel.getPlanes();
        List<FadeTransition> fadeTransition = transitionSeaside.getFadeTransition();

        fadeTransition.forEach(fade -> {
            if (pause) {
                if (fade != null && fade.getStatus() == Animation.Status.RUNNING) {
                    fade.pause();
                }
            }

            if (resume) {
                if (fade != null && fade.getStatus() == Animation.Status.PAUSED) {
                    fade.play();
                }
            }

            if (stop) {
                if (fade != null) {
                    fade.stop();
                }
            }
        });

        planes.forEach(plane -> {
            if (pause) {
                if (plane.getPlaneMovementAnimation() != null) {
                    plane.getPlaneMovementAnimation().pause();
                }
                if (plane.getRandomTransition() != null) {
                    plane.getRandomTransition().pause();
                }
                if (plane.getState() == State.SPAWNING) {
                    plane.getSpawnTransition().pause();
                }


            } else if (resume) {
                if (plane.getPlaneMovementAnimation() != null && plane.getStatusMovementAnimation().equals("PAUSED")) {
                    plane.getPlaneMovementAnimation().play();
                }
                if (plane.getLandingAnimation() != null && plane.getStatusMovementAnimation().equals("PAUSED")) {
                    plane.getLandingAnimation().play();
                }
                if (plane.getRandomTransition() != null) {
                    plane.getRandomTransition().play();
                }
                if (plane.getState() == State.SPAWNING) {
                    plane.getSpawnTransition().play();
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
                if (plane.getState() == State.SPAWNING) {
                    plane.getSpawnTransition().stop();
                }

                plane.setState(State.TERMINATED);
                plane.terminateAllAnimations();
            }
        });
    }

    public static void pause() {
        transitionSeaside.getSpawnCountDown().pause();
        pauseResumeOrStop(true, false, false);
    }

    public static void resume() {
        transitionSeaside.getSpawnCountDown().play();
        pauseResumeOrStop(false, true, false);
    }

    public static void stop() {
        transitionSeaside.getSpawnCountDown().stop();
        pauseResumeOrStop(false, false, true);
    }

    public void checkScore(final int score) {
         if (score < LIMIT_2100) {
            if (score >= LIMIT_500 && score < LIMIT_1000) {
                gameModel.setNumberPlanesToSpawnEachTime(2);
            } else if (score >= LIMIT_1000 && score <= LIMIT_1500) {
                gameModel.setNumberPlanesToSpawnEachTime(3);

            } else if (score >= LIMIT_2000) {
                gameModel.setNumberPlanesToSpawnEachTime(4);
            }
        }
    }
}
