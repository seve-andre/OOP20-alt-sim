package alt.sim.model.plane;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *  Describes the Sprite entity, rather than the rappresentation of a dinamic object in game (Plane)
 *
 *  it'll have:
 *  -   A background image,
 *  -   Coordinates x, y for the position in the Map,
 *  -   Width and height for the view rendering.
 *
*/
public class Sprite {

    /** path of the image location that is showed in the class Plane. */
    private static final String URL_SPRITE = ClassLoader.getSystemResource("images/airplane.png").toExternalForm();

    private Image imageSpriteToLoad;
    private ImageView imageSprite;
    private Point2D point;

    public Sprite() {
        this(new Point2D(0, 0));
    }

    public Sprite(final Point2D point) {
        this.imageSprite = new ImageView(new Image(URL_SPRITE));
        this.point = point;

    }

    public Sprite(final Image imageSpriteToLoad, final Point2D point) {
        this.imageSpriteToLoad = imageSpriteToLoad;
        this.imageSprite = new ImageView(this.imageSpriteToLoad);
        this.point = point;

    }

    public ImageView getImagePlane() {
        return this.imageSprite;
    }

    public Point2D getPoint() {
        return this.point;
    }

    public void setPoint2D(final Point2D point) {
        this.point = point;
    }
}
