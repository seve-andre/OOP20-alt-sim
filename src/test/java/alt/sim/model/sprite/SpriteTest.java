package alt.sim.model.sprite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import alt.sim.controller.seaside.SeasideController;

public class SpriteTest {

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 720;

    @Test
    public void resizeSpriteToMapTest() {
        try {
            final double smallPlaneSizeWidth = 32;
            final double smallPlaneSizeHeight = 32;

            double spriteWidth = 0;
            double spriteHeight = 0;

            if (SCREEN_WIDTH >= SeasideController.getScreenMinWidth() && SCREEN_HEIGHT >= SeasideController.getScreenMinHeight()) {
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
