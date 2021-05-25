package alt.sim.model.calculation;

import alt.sim.model.ImageResized;
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

    //Aggiungiamo questo campo di TEST
    private ImageResized imageSpriteResized;
    private Point2D point;
    private double x;
    private double y;

    public Sprite() {
        this(new Point2D(0, 0));

        this.x = 0;
        this.y = 0;
    }

    public Sprite(final Point2D point) {
        this.point = point;

        this.x = point.getX();
        this.y = point.getY();
    }

    /** 
     * @param url of the image to load
     */
    public Sprite(final String url) {
        this.x = 0;
        this.y = 0;

        setURLSprite(url);

        imageSpriteResized = new ImageResized(url);
        // loading the image into imageSpriteToLoad
        // this.loadImageSprite();

        ImageView imageSpriteView = new ImageView(new Image(url));

        this.imageSpriteResized.setLoadImage(imageSpriteView.getImage());
        this.imageSpriteResized.setImageSprite(imageSpriteView);
    }

    /**
     * 
     * @param imageSpriteToLoad to add in the imageSprite
     * @param point where place the Sprite 
     */
    public Sprite(final Image imageSpriteToLoad, final Point2D point) {
        /*
         * this.imageSpriteToLoad = imageSpriteToLoad; this.imageSprite = new
         * ImageView(this.imageSpriteToLoad);
         */

        /*
         * ImageView imageSpriteView = new ImageView(imageSpriteToLoad);
         * 
         * this.imageSpriteResized.setLoadImage(imageSpriteToLoad);
         * this.imageSpriteResized.setImageSprite(imageSpriteView);
         */
        this.point = point;

        this.x = point.getX();
        this.y = point.getY();
    }

    public ImageResized getImageSpriteResized() {
        return this.imageSpriteResized;
    }

    public ImageView getImage() { 
        return this.imageSpriteResized.getImageSprite(); 
    }

    public Point2D getPoint() {
        return this.point;
    }

    public void setPoint2D(final Point2D point) {
        this.point = point;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public static void setURLSprite(final String url) {
        Sprite.urlSprite = ClassLoader.getSystemResource(url).toExternalForm();
    }

    /** loading the Image specified from URL into ImageView. */
    /*
     * public void loadImageSprite() { this.imageSpriteToLoad = new
     * Image(Sprite.urlSprite); this.imageSprite = new
     * ImageView(this.imageSpriteToLoad); }
     */
}
