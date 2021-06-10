package alt.sim.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExplosionAnimation {

    private List<Image> images;
    private ImageView imageView;
    private int contImages;

    public ExplosionAnimation() {
        this.imageView = new ImageView();
        this.images = new ArrayList<Image>();
        this.contImages = 0;

        this.images.add(new Image("images/animations/explosion_1.png"));
        this.images.add(new Image("images/animations/explosion_2.png"));
        this.images.add(new Image("images/animations/explosion_3.png"));
        this.images.add(new Image("images/animations/explosion_4.png"));
        this.images.add(new Image("images/animations/explosion_5.png"));
        this.images.add(new Image("images/animations/explosion_6.png"));
        this.images.add(new Image("images/animations/explosion_7.png"));
    }

    public void getExplosionAnimation(final ImageView imageView) throws IOException {
        this.imageView = imageView;


            for (contImages = 0; contImages < images.size();) {

                Thread thread = new Thread(new Runnable() {
 
                    @Override
                    public void run() {
                        Runnable updater = new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                System.out.println("dentro RUN" + contImages);
                                if (contImages < images.size()) {
                                    System.out.println("dentro if: " + contImages);
                                    imageView.setImage(images.get(contImages));
                                    contImages++;
                                    
                                }
                            }
                        };

                        while (true) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                            }

                            // UI update is run on the Application thread
                            Platform.runLater(updater);
                        }
                    }
                });
                // don't let thread prevent JVM shutdown
                thread.setDaemon(true);
                thread.start();
            }
    }

}

