import alt.sim.model.plane.Plane;
import alt.sim.model.sprite.SpriteType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class SeasideTest extends Application {
    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        this.pane = new Pane();
        String urlImage = SpriteType.AIRPLANE.getURLImage();
        pane.resize(1000, 1000);

        ImageView img1 = new ImageView(new Image(urlImage));
        ImageView img2 = new ImageView(new Image(urlImage));
        ImageView img3 = new ImageView(new Image(urlImage));

        Plane p1 = new Plane(urlImage);
        Plane p2 = new Plane(urlImage);
        Plane p3 = new Plane(urlImage);

        p1.getSprite().setX(100);
        p2.getSprite().setX(300);
        p3.getSprite().setX(500);

        //img1.setX(100);
        //img2.setX(300);
        //img3.setX(500);

        pane.getChildren().add(p1.getSprite());
        pane.getChildren().add(p2.getSprite());
        pane.getChildren().add(p3.getSprite());

        removePlane(p1);

        Scene scene = new Scene(pane, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public void removePlane(final Plane plane){
        pane.getChildren().remove(plane.getSprite());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
