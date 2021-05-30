package alt.sim.model.calculation;

import alt.sim.model.RatioImpl;
import alt.sim.model.SpriteRedimensioned;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for managing the Image of the Plane and calculate his ProportionImage.
 */
public class ImageResized {

    private SpriteRedimensioned spriteResized;
    private Image loadImage;
    private ImageView imageSprite;

    /**
     * Initializes a newly created ImageResized object.
     * @param urlSprite
     * @param widthScreenConfrontation
     * @param heightScreenConfrontation
     * @param isPreserveRatio
     */
    public ImageResized(final String urlSprite, final double widthScreenConfrontation, final double heightScreenConfrontation, final boolean isPreserveRatio) {
        this.loadImage = new Image(urlSprite);
        this.imageSprite = new ImageView(loadImage);

        RatioImpl ratioSprite = new RatioImpl();
        RatioImpl ratioScreen = new RatioImpl();

        ratioSprite.setAntecedent(imageSprite.getBoundsInParent().getWidth());
        ratioSprite.setConsequent(imageSprite.getBoundsInParent().getHeight());
        ratioScreen.setAntecedent(widthScreenConfrontation);
        ratioScreen.setConsequent(heightScreenConfrontation);

        //Set the Ratio Sprite && Ratio Screen
        this.spriteResized = new SpriteRedimensioned(ratioScreen, ratioSprite, isPreserveRatio);
    }

    /**
     * @param isPreserveRatio indicate that the original dimension of the image is preserve.
     * executes the renderingProportionImage() method to apply the resize calculation for this ImageView values.
     * after do that, it update the width and height values of imageSprite.
     */
    public void resizeImageSprite(final boolean isPreserveRatio) {
        this.spriteResized.resizedBoundsSprite();

        this.imageSprite.setPreserveRatio(isPreserveRatio);
        this.imageSprite.setFitWidth(spriteResized.getResultBoundsSprite().getAntecedent());
        this.imageSprite.setFitHeight(spriteResized.getResultBoundsSprite().getConsequent());

    }

    /**
     * Modify the setPreserveRatio method of class ImageView that maintein the Ratio size of
     * an Image after the resize.
     * 
     * @param isPreserveRatio set the preserve ratio of the image when change the size.
     */
    public void setPreserveRatioSprite(final boolean isPreserveRatio) {
        this.imageSprite.setPreserveRatio(isPreserveRatio);
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
