package alt.sim.model.calculation;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *  Describes the Sprite entity, rather than the rappresentation of a dinamic object in game (Plane, Airstrip).
 *
 *  it'll have:
 *  -   A background image,
 *  -   Coordinates x, y for the position in the Map,
 *  -   Width and height for the view rendering.
 *
 */
public class Sprite {

    /** path of the image location that is showed in the class used. */
    private static String urlSprite;

    private Image imageSpriteToLoad;
    private ImageView imageSprite;
    private Point2D point;

    public Sprite() {
        this(new Point2D(0, 0));
    }

    public Sprite(final Point2D point) {
        this.point = point;
    }

    /** 
     * @param url of the image to load
     */
    public Sprite(final String url) {
        setURLSprite(url);
        // loading the image into imageSpriteToLoad
        this.loadImageSprite();
    }

    /**
     * 
     * @param imageSpriteToLoad to add in the imageSprite
     * @param point where place the Sprite 
     */
    public Sprite(final Image imageSpriteToLoad, final Point2D point) {
        this.imageSpriteToLoad = imageSpriteToLoad;
        this.imageSprite = new ImageView(this.imageSpriteToLoad);
        this.point = point;

    }

    public ImageView getImage() {
        return this.imageSprite;
    }

    public Point2D getPoint() {
        return this.point;
    }

    public void setPoint2D(final Point2D point) {
        this.point = point;
    }

    public static void setURLSprite(final String url) {
        Sprite.urlSprite = ClassLoader.getSystemResource(url).toExternalForm();
    }

    /** loading the Image specified from URL into ImageView. */
    public void loadImageSprite() {
        this.imageSpriteToLoad = new Image(Sprite.urlSprite);
        this.imageSprite = new ImageView(this.imageSpriteToLoad);
    }
}
