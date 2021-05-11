package alt.sim.model;

import alt.sim.utility.MultiplatformUtility;
import alt.sim.view.MainPlaneView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for managing the Image of the Plane and calculate his ProportionImage.
 */
public class ImageResized {

    /** URL Path of the image to load. */
    private static final String URL_SPRITE = "file:" + MultiplatformUtility.SEPARATOR
            + MultiplatformUtility.SEPARATOR + MultiplatformUtility.SEPARATOR + "Users"
            + MultiplatformUtility.SEPARATOR + "daniel" + MultiplatformUtility.SEPARATOR
            + "eclipse-workspace" + MultiplatformUtility.SEPARATOR + "PlaneModel"
            + MultiplatformUtility.SEPARATOR  + "src" + MultiplatformUtility.SEPARATOR
            + "application.resources" + MultiplatformUtility.SEPARATOR + "Plane.png";

    private ProportionImage proportionImageResized;
    private Image loadImage;
    private ImageView imageSprite;

    /**
     * Initializes a newly created ImageResized object.
     */
    public ImageResized() {
        this.loadImage = new Image(URL_SPRITE);
        this.imageSprite = new ImageView(loadImage);

        double widthImage = loadImage.getWidth();
        double heightImage = loadImage.getHeight();

        proportionImageResized = new ProportionImage();
        proportionImageResized.setRatioImage(new Ratio(widthImage, heightImage));
        proportionImageResized.setRatioScreen(new Ratio(
                MainPlaneView.getScreenWidth(), MainPlaneView.getScreenWidth()
        ));
    }

    /**
     * executed the renderingProportionImage() method to apply the resize calculation for this ImageView values.
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
     * @return return the imageSprite proportioned with the Screen.
     */
    public ImageView getImageSprite() {
        return this.imageSprite;
    }
}
