package alt.sim.model;

import java.awt.Image;

import javax.swing.text.html.ImageView;

import alt.sim.view.MainPlaneView;

/**
 * Class for managing the Image of the Plane and calculate his ProportionImage.
 */
public class ImageResized {

    /** URL Path of the image to load. */
    private static final String URL_SPRITE = "file:" + MultiplatformUtility.KERNEL_SEPARATOR
            + MultiplatformUtility.KERNEL_SEPARATOR + MultiplatformUtility.KERNEL_SEPARATOR + "Users"
            + MultiplatformUtility.KERNEL_SEPARATOR + "daniel" + MultiplatformUtility.KERNEL_SEPARATOR
            + "eclipse-workspace" + MultiplatformUtility.KERNEL_SEPARATOR + "PlaneModel"
            + MultiplatformUtility.KERNEL_SEPARATOR  + "src" + MultiplatformUtility.KERNEL_SEPARATOR
            + "application.resources" + MultiplatformUtility.KERNEL_SEPARATOR + "Plane.png";

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
