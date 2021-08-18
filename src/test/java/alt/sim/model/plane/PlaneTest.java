package alt.sim.model.plane;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaneTest {

    @Test
    public void removeDuplicateInLinesPathTest() {
        //TODO Terminare
        List<Point2D> linesPath = new ArrayList<Point2D>();
        Random r = new Random(50);

        for (int i = 0; i < 50; i++) {
            linesPath.add(new Point2D(r.nextDouble(), r.nextDouble()));
            System.out.println(linesPath.get(i).getX() + " , " + linesPath.get(i).getY());
        }

        double xCopy;
        double yCopy;

        for (int k = 0; k < linesPath.size(); k++) {
            xCopy = linesPath.get(k).getX();
            yCopy = linesPath.get(k).getY();

            for (int j = 0; j < linesPath.size(); j++) {
                if (j != k && linesPath.get(j).getX() == xCopy && linesPath.get(j).getY() == yCopy) {
                    linesPath.remove(j);
                    j--;
                }
            }
        }
    }
}
