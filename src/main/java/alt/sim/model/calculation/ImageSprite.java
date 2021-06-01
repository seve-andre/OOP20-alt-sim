package alt.sim.model.calculation;

import alt.sim.model.SpriteRedimensioned;
import alt.sim.view.MainPlaneView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for managing the Image of the Plane and calculate his ProportionImage.
 */
public class ImageSprite {

    /** URL Path of the image to load. */
    //private static String urlSprite;
    private final String urlSprite;

    private SpriteRedimensioned proportionImageResized;
    private Image loadImage;
    private ImageView imageSprite;
    private double widthImage;
    private double heightImage;

    /**
     * Initializes a newly created ImageResized object.
     * @param url
     */
    public ImageSprite(final String url) {
        this.urlSprite = (url);

        this.loadImage = new Image(urlSprite);
        this.imageSprite = new ImageView(loadImage);

        this.widthImage = loadImage.getWidth();
        this.heightImage = loadImage.getHeight();

        proportionImageResized = new SpriteRedimensioned();
        proportionImageResized.setRatioSpriteValue(widthImage, heightImage);
        proportionImageResized.setRatioScreenValue(MainPlaneView.getScreenWidth(), MainPlaneView.getScreenHeight());

        // Resize the ImageView
        resizeImageSprite(true);
    }

    public ImageSprite(final String url, final double screenWidth, final double screenHeight, final boolean isPreserveRatio) {
        this(url);
    }

    /**
     * @param isRatioPreserve defined if the dimension image is preserved or not.
     *
     * executes the renderingProportionImage() method to apply the resize calculation for this ImageView values.
     * after do that, it update the width and height values of imageSprite.
     */
    public void resizeImageSprite(final boolean isRatioPreserve) {
        double widthResized = 0;
        double heightResized = 0;

        // Setting the Sprite Image with preserved ratio
        setImagePreserveRatio(isRatioPreserve);

        this.proportionImageResized.resizeBoundsSprite();
        widthResized = proportionImageResized.getResultBoundsSprite().getAntecedent();
        heightResized = proportionImageResized.getResultBoundsSprite().getConsequent();

        this.imageSprite.setFitWidth(widthResized);
        this.imageSprite.setFitHeight(heightResized);
    }

    /**
     * @return the imageSprite proportioned to the Screen.
     */
    public ImageView getImageSprite() {
        return this.imageSprite;
    }

    public String getUrlSprite() {
        return this.urlSprite;
    }

    public void setImagePreserveRatio(final boolean isRatioPreserve) {
        this.imageSprite.setPreserveRatio(isRatioPreserve);
    }

    public double getWidthImage() {
        return this.widthImage;
    }

    public double getHeightImage() {
        return this.heightImage;
    }
}
