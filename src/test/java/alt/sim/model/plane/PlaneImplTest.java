package alt.sim.model.plane;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import alt.sim.model.sprite.SpriteType;
import javafx.geometry.Point2D;

public class PlaneImplTest {

    private static final int TIME_TO_TIMEOUT = 4500;
    private static final int N_POINTS = 50;
    private static final int RANDOM_BOUND = 10;

    @Test
    public void removeDuplicateInLinesPathTest() {
        List<Point2D> linesPath = new ArrayList<>();

        for (int i = 0; i < N_POINTS; i++) {
            linesPath.add(
                    new Point2D(
                            ThreadLocalRandom.current().nextInt(RANDOM_BOUND),
                            ThreadLocalRandom.current().nextInt(RANDOM_BOUND)
                            )
                    );
        }

        int intialSize = linesPath.size();
        linesPath = removeDuplicatesTest(linesPath);

        //Check if list contains duplicates

        for (int k = 0; k < linesPath.size() - 1; k++) {
            Point2D compareValue = linesPath.get(k);

            Assertions.assertFalse(compareValue.getX() == linesPath.get(k + 1).getX()
                    && compareValue.getY() == linesPath.get(k + 1).getY());
        }

        Assertions.assertTrue(linesPath.size() <= intialSize);
    }

    public List<Point2D> removeDuplicatesTest(final List<Point2D> linesPathDuplicated) {
        return linesPathDuplicated.stream()
                .distinct()
                .collect(Collectors.toList());
    }



    @Test()
    public void isMoreThanOneSelectedTest() {
        List<Boolean> planeSelected = new ArrayList<Boolean>(
                Arrays.asList(true, false, false, false, false));

        int planeBeenSelected = 0;

        for (Boolean b:planeSelected) {
            if (b) {
                planeBeenSelected++;
            }
        }

        Assertions.assertTrue(planeBeenSelected <= 1 && planeBeenSelected >= 0);
    }

    @Test()
    public void generatePlaneTest() {
        String urlAirplane = SpriteType.AIRPLANE.getURLImage();

        Assertions.assertSame(true, urlAirplane.contains("images/map_components/"));
    }

    @Test()
    public void planeStatusTest() {
        assertTimeout(ofMillis(TIME_TO_TIMEOUT), () -> {
        });
    }
}
