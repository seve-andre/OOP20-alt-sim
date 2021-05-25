package alt.sim.model;

import alt.sim.view.MainPlaneView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for managing the Image of the Plane and calculate his ProportionImage.
 */
public class ImageResized {

    /** URL Path of the image to load. */
    private static String urlSprite;

    private ProportionImage proportionImageResized;
    private Image loadImage;
    private ImageView imageSprite;

    /**
     * Initializes a newly created ImageResized object.
     * @param url
     */
    public ImageResized(final String url) {
        urlSprite = ClassLoader
                .getSystemResource(url)
                .toExternalForm();
        this.loadImage = new Image(urlSprite);
        this.imageSprite = new ImageView(loadImage);

        double widthImage = loadImage.getWidth();
        double heightImage = loadImage.getHeight();

        proportionImageResized = new ProportionImage();
        proportionImageResized.setRatioImage(new RatioImpl(widthImage, heightImage));
        proportionImageResized.setRatioScreen(new RatioImpl(
                MainPlaneView.getScreenWidth(), MainPlaneView.getScreenWidth()
        ));
    }

    /**
     * executes the renderingProportionImage() method to apply the resize calculation for this ImageView values.
     * after do that, it update the width and height values of imageSprite.
     */
    public void resizeImageSprite() {
        double widthResized = 0;
        double heightResized = 0;

        this.proportionImageResized.renderingProportionImage();
        widthResized = proportionImageResized.getResultOfProportion().getAntecedent();
        heightResized = proportionImageResized.getResultOfProportion().getConsequent();

        this.imageSprite.setFitWidth(widthResized);
        this.imageSprite.setFitHeight(heightResized);
    }

    /**
     * @return the imageSprite proportioned to the Screen.
     */
    public ImageView getImageSprite() {
        return this.imageSprite;
    }

    public void setImageSprite(final ImageView imageSprite) {
        this.imageSprite = imageSprite;
    }

    public Image getLoadImage() {
        return this.loadImage;
    }

    public void setLoadImage(final Image loadImage) {
        this.loadImage = loadImage;
    }
}
