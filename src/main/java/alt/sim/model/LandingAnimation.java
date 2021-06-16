package alt.sim.model;

import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LandingAnimation {
    private ImageView spriteToApplyAnimation;
    private ScaleTransition landingAnimation;

    public LandingAnimation(final Point2D positionAnimation, final ImageView spriteToApplyAnimation) {
        this.spriteToApplyAnimation = spriteToApplyAnimation;
        this.landingAnimation = new ScaleTransition();
    }

    private void settingLandingAnimation() {
        //Setting the duration for the transition 
        landingAnimation.setDuration(Duration.millis(2000)); 

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

    public ScaleTransition getLandingAnimation() {
        // Setting landingAnimation before return
        settingLandingAnimation();

        return landingAnimation;
    }
}
