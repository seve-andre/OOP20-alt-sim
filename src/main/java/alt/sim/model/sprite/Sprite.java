package alt.sim.model.sprite;

import alt.sim.Main;
import alt.sim.view.seaside.Seaside;
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
    private ImageView sprite;

    /**
     * @param urlSprite contains url of the image to load
     */
    public Sprite(final String urlSprite) {
        try {
            Image bufferedSprite = new Image(urlSprite);
            this.sprite = new ImageView(bufferedSprite);
        } catch (RuntimeException re) {
            System.out.println("Wrong bufferedSprite or sprite intialization: " + re.getMessage());
        }

        //resize calculation:
        resizeSpriteToMap();
    }

    //----------------------------------------------------------------

    /**
     * Method to resize the image of the Sprite according to the size of the Main Screen.
     */
    private void resizeSpriteToMap() {
        double smallPlaneSizeHeight = 32;
        double smallPlaneSizeWidth = 32;

        if (Main.getStage().getWidth() >= Seaside.getScreenMinWidth() && Main.getStage().getHeight() >= Seaside.getScreenMinHeight()) {
            this.sprite.setFitWidth((smallPlaneSizeWidth * 2));
            this.sprite.setFitHeight((smallPlaneSizeHeight * 2));
        } else {
            this.sprite.setFitWidth(smallPlaneSizeWidth);
            this.sprite.setFitHeight(smallPlaneSizeHeight);
        }
    }

    /**
     * @param newUrlImage the new Image to set into Sprite.
     */
    public void setSpritePlane(final String newUrlImage) {
        this.sprite.setImage(new Image(newUrlImage));
    }

    /**
     * @return the ImageView of the Sprite.
     */
    public ImageView getSprite() {
        return this.sprite;
    }
}
