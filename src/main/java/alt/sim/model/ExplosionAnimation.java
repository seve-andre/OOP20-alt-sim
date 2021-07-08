package alt.sim.model;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplosionAnimation {
    private static final int DURATION_KEYFRAME = 20;
    private static final int TRANSITION_ANIMATION_DURATION = 1000;

    private ImageView imgExplosion;
    private KeyFrame keyframe;
    private Timeline timer;

    private int contImage = 1;

    public ExplosionAnimation() {
        this.imgExplosion = new ImageView(new Image("images/animations/explosion_1.png"));
    }

    public ExplosionAnimation(final Point2D positionAnimation) {
        this();
        imgExplosion.setX(positionAnimation.getX());
        imgExplosion.setY(positionAnimation.getY());
    }

    public void startExplosion() {
        keyframe = new KeyFrame(Duration.millis(DURATION_KEYFRAME), (ActionEvent loopEvent) -> {
            imgExplosion.imageProperty().set(new Image("images/animations/explosion_" + contImage + ".png"));
            contImage++;
        });
       
        ScaleTransition scaleExplosionAnimation = new ScaleTransition();
        
        scaleExplosionAnimation.setNode(imgExplosion);

        scaleExplosionAnimation.setFromX(0);
        scaleExplosionAnimation.setFromY(0);
        scaleExplosionAnimation.setToX(1);
        scaleExplosionAnimation.setToY(1);

        scaleExplosionAnimation.setAutoReverse(true);
        scaleExplosionAnimation.setCycleCount(2);
        scaleExplosionAnimation.setDuration(Duration.millis(TRANSITION_ANIMATION_DURATION));

        scaleExplosionAnimation.play();

        timer = new Timeline(keyframe);
        timer.setCycleCount(50);
        timer.play();
    }

    public Timeline getTimeline() {
        return this.timer;
    }

    public ImageView getImgExplosion() {
        return this.imgExplosion;
    }
}
