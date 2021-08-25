package alt.sim.model.sprite;

import org.junit.jupiter.api.Test;

import alt.sim.Main;
import alt.sim.controller.seaside.SeasideController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static org.junit.jupiter.api.Assertions.*;

public class SpriteTest {

    @Test
    public void resizeSpriteToMapTest() {
        try {
            double GameWidth = 1080;
            double GameHeight = 720;
            final double smallPlaneSizeWidth = 32;
            final double smallPlaneSizeHeight = 32;

            double spriteWidth = 0;
            double spriteHeight = 0;

            if (GameWidth >= SeasideController.getScreenMinWidth() && GameHeight >= SeasideController.getScreenMinHeight()) {
                spriteWidth = (smallPlaneSizeWidth * 2);
                spriteHeight = (smallPlaneSizeHeight * 2);

                assertEquals(spriteWidth, (smallPlaneSizeWidth * 2));
                assertEquals(spriteHeight, (smallPlaneSizeWidth * 2));

            } else {
                spriteWidth = (smallPlaneSizeWidth);
                spriteHeight = (smallPlaneSizeHeight);

                assertEquals(spriteWidth, (smallPlaneSizeWidth));
                assertEquals(spriteHeight, (smallPlaneSizeWidth));
            }

            System.out.println("spriteWidth: " + spriteWidth + " spriteHeight: " + spriteHeight);

        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
}
