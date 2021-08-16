package alt.sim.model.plane;

import alt.sim.model.sprite.SpriteType;
import javafx.geometry.Bounds;
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
    private final double smallPlaneSizeWidth = 32;
    private final double smallPlaneSizeHeight = 32;

    private Image bufferedSprite;
    private ImageView sprite;

    public Sprite(final SpriteType type) {
        this(type.getURLImage());
    }

    /**
     * @param urlSprite contains url of the image to load
     */
    public Sprite(final String urlSprite) {
        this.bufferedSprite = new Image(urlSprite);
        this.sprite = new ImageView(bufferedSprite);

        //resize calculation:
        resizeSpriteToMap(false);
    }

    //----------------------------------------------------------------

    /**
     * Metodo per ridimensionare il l'immagine della Sprite a seconda
     * della dimensione dello Schermo principale.
     *
     * @param isPreserveRatio: specifica se l'immagine ridimensionata, mantiene il rapporto originale
     */
    private void resizeSpriteToMap(final boolean isPreserveRatio) {
        sprite.setPreserveRatio(isPreserveRatio);

        //if (Seaside.SCREEN_BOUND.getWidth() >= MIN_SCREEEN_RANGE_WIDTH && Seaside.SCREEN_BOUND.getHeight() >= MIN_SCREEEN_RANGE_HEIGHT) {
        this.sprite.setFitWidth((smallPlaneSizeWidth * 2));
        this.sprite.setFitHeight((smallPlaneSizeHeight * 2));
        //} else {
        //  this.sprite.setFitWidth(smallPlaneSizeWidth);
        // this.sprite.setFitHeight(smallPlaneSizeHeight);
        //}
    }

    public ImageView getSprite() {
        return this.sprite;
    }

    public void setX(final double x) {
        //this.centerX = calculateCenterX(x);

        this.sprite.setX(x);
    }

    public void setY(final double y) {
        //this.centerY = calculateCenterY(y);

        this.sprite.setY(y);
    }

    public double getCenterX() {
        return this.getParentBound().getCenterX();
    }

    public double getCenterY() {
        return this.getParentBound().getCenterY();
    }

    public Bounds getLocalBound() {
        return this.sprite.getBoundsInLocal();
    }

    public Bounds getParentBound() {
        return this.sprite.getBoundsInParent();
    }

    public void setSpritePlane(final String newUrlImage) {
        this.sprite.setImage(new Image(newUrlImage));
    }
}
