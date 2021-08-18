package alt.sim.model.sprite;

import alt.sim.Main;
import alt.sim.view.seaside.Seaside;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpriteTest {

    @Test
    public void resizeSpriteToMapTest() {
        try {
            Image bufferedSprite = new Image(SpriteType.AIRSTRIP.getURLImage());
            ImageView sprite = new ImageView();
            final double smallPlaneSizeWidth = 32;
            final double smallPlaneSizeHeight = 32;

            assertNull(sprite, "Sprite wrong initialization");
            assertNull(bufferedSprite, "Image wrong initialization");

            if (Main.getStage().getWidth() >= Seaside.getScreenMinWidth() && Main.getStage().getHeight() >= Seaside.getScreenMinHeight()) {
                sprite.setFitWidth((smallPlaneSizeWidth * 2 + 1));
                sprite.setFitHeight((smallPlaneSizeHeight * 2 + 1));

                assertNotEquals(sprite.getBoundsInLocal().getWidth(), (smallPlaneSizeWidth * 2));
                assertNotEquals(sprite.getBoundsInLocal().getHeight(), (smallPlaneSizeWidth * 2));

            } else {
                sprite.setFitWidth(smallPlaneSizeWidth);
                sprite.setFitHeight(smallPlaneSizeHeight);

                assertNotEquals(sprite.getBoundsInLocal().getWidth(), (smallPlaneSizeWidth));
                assertNotEquals(sprite.getBoundsInLocal().getHeight(), (smallPlaneSizeWidth));
            }

        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
}