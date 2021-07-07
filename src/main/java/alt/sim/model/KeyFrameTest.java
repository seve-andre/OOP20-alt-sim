package alt.sim.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class KeyFrameTest {
    private static final  int DURATION_KEYFRAME = 100;

    private ImageView imgExplosion;
    private KeyFrame keyframe;
    private Timeline timer;

    private int contImage = 1;

    public KeyFrameTest(final Point2D positionAnimation) {
        imgExplosion = new ImageView(new Image("images/animations/explosion_1.png"));
        imgExplosion.setX(positionAnimation.getX());
        imgExplosion.setY(positionAnimation.getY());
    }

    public void startExplosion() {
        keyframe = new KeyFrame(Duration.millis(DURATION_KEYFRAME), (ActionEvent loopEvent) -> {
            imgExplosion.imageProperty().set(new Image("images/animations/explosion_" + contImage + ".png"));
            contImage++;
        });

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
