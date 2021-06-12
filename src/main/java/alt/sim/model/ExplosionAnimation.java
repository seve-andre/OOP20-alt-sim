package alt.sim.model;

import java.util.ArrayList;
import java.util.List;

import alt.sim.model.plane.Plane;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;

public class ExplosionAnimation {
    private static final  int NUMBER_IMAGES_ANIMATION = 67;

    private List<Image> images;
    private Plane planeSprite;
    private int contImages;

    public ExplosionAnimation() {
        this.images = new ArrayList<Image>();
        this.contImages = 0;

        for (int i = 1; i < NUMBER_IMAGES_ANIMATION; i++) {
            this.images.add(new Image("images/animations/explosion_" + i + ".png"));
        }
    }

    public ExplosionAnimation(final Plane planeSprite) {
        this();

        this.planeSprite = planeSprite;
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
                        if (contImages < images.size()) {
                            planeSprite.getImagePlane().setImage(images.get(contImages));
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

}

