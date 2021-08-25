package alt.sim.model.animation;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation implements AnimationPlane {
    private static final int DURATION_LANDING_ANIMATION = 1000;

    private ImageView spriteAnimated;
    private ScaleTransition scaleAnimation;

    public Animation(final ImageView spriteAnimated) {
        this.spriteAnimated = spriteAnimated;
        this.scaleAnimation = new ScaleTransition();
        this.settingDefaultAnimationOptions();
    }

    /**
     * Set the TransitionAnimation with right defaults values.
     */
    public void settingDefaultAnimationOptions() {
        //Setting the duration for the transition
        scaleAnimation.setDuration(Duration.millis(DURATION_LANDING_ANIMATION));

        //Setting the node for the transition
        scaleAnimation.setNode(this.spriteAnimated);

        //Setting the final dimensions for scaling
        scaleAnimation.setToX(0);
        scaleAnimation.setToY(0);

        //Setting the cycle count for the translation
        scaleAnimation.setCycleCount(1);

        //Setting auto reverse value to true
        scaleAnimation.setAutoReverse(false);
    }

    /**
     * @return LandingAnimation setted and ready to play.
     */
    public ScaleTransition getLandingAnimation() {
        settingDefaultAnimationOptions();

        return scaleAnimation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        settingDefaultAnimationOptions();
        getLandingAnimation().play();
    }

    /**
     * @return animation duration value GETTER.
     */
    public static double getDurationAnimation() {
        return DURATION_LANDING_ANIMATION;
    }
}
