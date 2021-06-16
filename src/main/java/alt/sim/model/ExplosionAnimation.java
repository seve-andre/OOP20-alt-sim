package alt.sim.model;

import alt.sim.model.plane.AnimationPlane;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Implement Animation of explosion for the Plane when collid.
 * Is realizated with TransitionAnimation in parallel with explosion
 *
 */
public class ExplosionAnimation extends AnimationPlane {
    /** Number of the images for the animation. */
    private static final  int NUMBER_IMAGES_ANIMATION = 50;

    /** Images size. */
    private static final  double IMAGE_WIDTH_RATE = 69;
    private static final  double IMAGE_HEIGHT_RATE = 73;

    private static final  int TRANSITION_ANIMATION_DURATION = 500;


    private List<Image> images;
    private ImageView spriteToApplyAnimation;
    private ScaleTransition scaleExplosionAnimation;
    private int contImages;

    public ExplosionAnimation() {
        this.scaleExplosionAnimation = new ScaleTransition();
        this.spriteToApplyAnimation = new ImageView();
        this.images = new ArrayList<Image>();
        this.contImages = 0;

        for (int i = 1; i < NUMBER_IMAGES_ANIMATION; i++) {
            this.images.add(new Image("images/animations/explosion_" + i + ".png"));
        }
    }

    /**
     * @param positionAnimation set the position where activate the animation.
     */
    public ExplosionAnimation(final Point2D positionAnimation) {
        this();

        setPositionAnimation(positionAnimation);
        centerAnimation();
    }

    /** implement the ScaleTransition synchronizated with the explosion. */
    private void startingParallelScaleAnimation() {
        scaleExplosionAnimation.setNode(spriteToApplyAnimation);

        scaleExplosionAnimation.setFromX(0);
        scaleExplosionAnimation.setFromY(0);
        scaleExplosionAnimation.setToX(1);
        scaleExplosionAnimation.setToY(1);

        scaleExplosionAnimation.setAutoReverse(true);
        scaleExplosionAnimation.setCycleCount(2);
        scaleExplosionAnimation.setDuration(Duration.millis(TRANSITION_ANIMATION_DURATION));

        scaleExplosionAnimation.play();
    }

    /**
     * @return the ExplosionAnimation setted and ready to start.
     */
    public AnimationTimer getExplosionAnimation() {

        class MyTimer extends AnimationTimer {

            @Override
            public void handle(final long now) {
                try {
                    startingExplosionAniamtion();
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                }
            }

            private void startingExplosionAniamtion() throws InterruptedException {
                startingParallelScaleAnimation();

                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        if (contImages < images.size()) {
                            spriteToApplyAnimation.setImage(images.get(contImages));
                            contImages++;
                        } else {
                            stop();
                        }
                    }
                });
            }
        }
        return new MyTimer();
    }

    /** center the image with the position where it started. */
    private void centerAnimation() {
        this.spriteToApplyAnimation.setX(spriteToApplyAnimation.getX() - (IMAGE_WIDTH_RATE / 2));
        this.spriteToApplyAnimation.setY(spriteToApplyAnimation.getY() - (IMAGE_HEIGHT_RATE / 2));
    }

    /**
     * @return the ImageView that contain the frame of images.
     */
    public ImageView getSpriteToApplyAnimation() {
        return this.spriteToApplyAnimation;
    }

    @Override
    public void startAnimation() {
        getExplosionAnimation();
    }

    @Override
    public void setPositionAnimation(final Point2D positionAnimation) {
        this.spriteToApplyAnimation.setX(positionAnimation.getX());
        this.spriteToApplyAnimation.setY(positionAnimation.getY());
    }

}


