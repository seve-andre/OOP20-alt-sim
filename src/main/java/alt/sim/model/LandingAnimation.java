package alt.sim.model;

import alt.sim.model.plane.AnimationPlane;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LandingAnimation extends AnimationPlane {
    private static final int DURATION_LANDING_ANIMATION = 1000;

    private ImageView spriteToApplyAnimation;
    private ScaleTransition landingAnimation;

    public LandingAnimation(final ImageView spriteToApplyAnimation) {
        this.spriteToApplyAnimation = spriteToApplyAnimation;
        this.landingAnimation = new ScaleTransition();
    }

    /**
     * Set the TransitionAnimation with right values.
     */
    private void settingLandingAnimation() {
        //Setting the duration for the transition 
        landingAnimation.setDuration(Duration.millis(DURATION_LANDING_ANIMATION)); 

        //Setting the node for the transition 
        landingAnimation.setNode(this.spriteToApplyAnimation); 

        //Setting the final dimensions for scaling 
        landingAnimation.setToX(0);
        landingAnimation.setToY(0); 

        //Setting the cycle count for the translation 
        landingAnimation.setCycleCount(1); 

        //Setting auto reverse value to true 
        landingAnimation.setAutoReverse(false); 
    }

    /**
     * @return LandingAnimation setted and ready to play.
     */
    public ScaleTransition getLandingAnimation() {
        // Setting landingAnimation before return
        settingLandingAnimation();

        return landingAnimation;
    }

    @Override
    public void startAnimation() {
        settingLandingAnimation();
        getLandingAnimation().play();
    }

    @Override
    public void setPositionAnimation(final Point2D positionAnimation) {
        // TODO Auto-generated method stub
    }
}
