package alt.sim.model;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplosionAnimation {
    private static final  int NUMBER_IMAGES_ANIMATION = 50;

    private static final  double IMAGE_WIDTH_RATE = 69;
    private static final  double IMAGE_HEIGHT_RATE = 73;

    private List<Image> images;
    private ImageView spriteToApplyAnimation;
    private ScaleTransition scaleExplosionAnimation;
    private int contImages;

    public ExplosionAnimation() {
        this.spriteToApplyAnimation = new ImageView();
        this.images = new ArrayList<Image>();
        this.contImages = 0;

        for (int i = 1; i < NUMBER_IMAGES_ANIMATION; i++) {
            this.images.add(new Image("images/animations/explosion_" + i + ".png"));
        }
    }

    public ExplosionAnimation(final Point2D positionOfAnimation) {
        this();

        this.spriteToApplyAnimation.setX(positionOfAnimation.getX());
        this.spriteToApplyAnimation.setY(positionOfAnimation.getY());
        centerAnimation();
    }

    private void startingParallelScaleAnimation() {
        scaleExplosionAnimation.setNode(spriteToApplyAnimation);

        scaleExplosionAnimation.setFromX(0);
        scaleExplosionAnimation.setFromY(0);
        scaleExplosionAnimation.setToX(1);
        scaleExplosionAnimation.setToY(1);

        scaleExplosionAnimation.setAutoReverse(true);
        scaleExplosionAnimation.setCycleCount(2);
        scaleExplosionAnimation.setDuration(Duration.millis(500));

        scaleExplosionAnimation.play();
    }

    public AnimationTimer getExplosionAnimation() {

        class MyTimer extends AnimationTimer {

            @Override
            public void handle(long now) {
                try {
                    startingExplosionAniamtion();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }

            private void startingExplosionAniamtion() throws InterruptedException {

                Platform.runLater(new Runnable() {

                    @Override public void run() {
                        startingParallelScaleAnimation();

                        if (contImages < images.size()) {
                            spriteToApplyAnimation.setImage(images.get(contImages));
                            contImages++;
                        } else {
                            System.out.println("Stop Explosion Animation");
                            stop();
                        }
                    }
                });
            }
        }
        return new MyTimer();
    }

    private void centerAnimation() {
        this.spriteToApplyAnimation.setX(spriteToApplyAnimation.getX() - (IMAGE_WIDTH_RATE / 2));
        this.spriteToApplyAnimation.setY(spriteToApplyAnimation.getY() - (IMAGE_HEIGHT_RATE / 2));
    }

    public ImageView getSpriteToApplyAnimation() {
        return this.spriteToApplyAnimation;
    }

}

