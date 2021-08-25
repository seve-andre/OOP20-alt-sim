package alt.sim.model.animation;

import javafx.scene.image.ImageView;

/**
 * Create LandingAnimation for implement ScaleTransition animation when the Plane Landing in the Airstrip.
 */
public class LandingAnimation extends Animation {

    public LandingAnimation(final ImageView spriteToApplyAnimation) {
        super(spriteToApplyAnimation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        settingDefaultAnimationOptions();
        getLandingAnimation().play();
    }
}
