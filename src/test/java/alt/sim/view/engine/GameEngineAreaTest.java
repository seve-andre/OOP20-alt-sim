package alt.sim.view.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class GameEngineAreaTest {

    private static final int X_POSITION = 1061;
    private static final int Y_POSITION = 500;
    private static final int MAX_WIDTH = 1080;
    private static final int MAX_HEIGHT = 720;
    private static final int RECTANGLE_WIDTH = 20;
    private static final int RECTANGLE_LENGTH = 20;
    @Test
    public void checkOutOfBoundsTest() {
        Rectangle rect = new Rectangle(0, 0, RECTANGLE_WIDTH, RECTANGLE_LENGTH);
        double rectPositionX = X_POSITION;
        double rectPositionY = Y_POSITION;

        double rectMinWidth = rectPositionX;
        double rectMaxWidth = rectPositionX + rect.getWidth();
        double rectMinHeight = rectPositionY;
        double rectMaxHeight = rectPositionY + rect.getHeight();

        boolean outOfBounds = false;


        if (rectMinWidth < 0
                || rectMaxWidth > MAX_WIDTH
                || rectMinHeight < 0
                || rectMaxHeight > MAX_HEIGHT) {
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

        Rectangle r1 = new Rectangle(0, 0, RECTANGLE_WIDTH, RECTANGLE_LENGTH);
        Rectangle r2 = new Rectangle(0, 0, RECTANGLE_WIDTH, RECTANGLE_LENGTH);
        Rectangle r3 = new Rectangle(0, 0, RECTANGLE_WIDTH, RECTANGLE_LENGTH);

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
