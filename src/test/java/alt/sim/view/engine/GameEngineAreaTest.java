package alt.sim.view.engine;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameEngineAreaTest {

    @Test
    public void checkOutOfBoundsTest() {
       Rectangle rect = new Rectangle(0, 0, 20, 20);
        double rectPositionX = 1061;
        double rectPositionY = 500;

        double rectMinWidth = rectPositionX;
        double rectMaxWidth = rectPositionX + rect.getWidth();
        double rectMinHeight = rectPositionY;
        double rectMaxHeight = rectPositionY + rect.getHeight();

        boolean outOfBounds = false;


        if (rectMinWidth < 0
                || rectMaxWidth > 1080
                || rectMinHeight < 0
                || rectMaxHeight > 720) {
            outOfBounds = true;
            System.out.println("FUORI BORDO");
        } else {
            System.out.println("Dentro Map");
            outOfBounds = false;
        }

        Assertions.assertEquals(true, outOfBounds);

    }

    @Test
    public void removePlanesTest() {
            Pane pane = new Pane();

            Rectangle r1 = new Rectangle(0, 0, 20, 20);
            Rectangle r2 = new Rectangle(0, 0, 20, 20);
            Rectangle r3 = new Rectangle(0, 0, 20, 20);

            List<Rectangle> rectangles = new ArrayList<>(
                    Arrays.asList(r1, r2, r2, r3, r3, r3)
            );
            final List<Rectangle> rects = rectangles.stream()
                    .distinct()
                    .collect(Collectors.toList());

            //Check duplicate object in pane
            rectangles = rects;

            for (Rectangle r : rectangles) {
                pane.getChildren().add(r);
            }

            pane.getChildren().removeAll(rectangles);
            Assertions.assertEquals(0, pane.getChildren().size());
    }
}
