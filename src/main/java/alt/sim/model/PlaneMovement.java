package alt.sim.model;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

public class PlaneMovement {
    private static final int COORDINATES_LIMIT = 200;

    private Point2D[] planeCoordinates;

    public PlaneMovement() {
        this.planeCoordinates = new Point2D[COORDINATES_LIMIT];
    }

    private void alignmentCoordinatesArray(final List<Point2D> planeCoordinates) {
        for (Point2D pointPassed:planeCoordinates) {
            System.out.println(pointPassed.getX() + " | " + pointPassed.getY());
        }

        if (this.planeCoordinates.length > planeCoordinates.size()) {
            //this.planeCoordinates = Arrays.copyOf(new Point2D[planeCoordinates.size()], planeCoordinates.size());
            this.planeCoordinates = new Point2D[planeCoordinates.size()];

            for (int j = 0; j < planeCoordinates.size(); j++) {
                this.planeCoordinates[j] = planeCoordinates.get(j);
            }
        } else if (planeCoordinates.size() == COORDINATES_LIMIT) {
            this.planeCoordinates = (Point2D[]) Arrays.copyOf(planeCoordinates.toArray(), planeCoordinates.size());
        }
    }

    public void setPlaneCoordinates(final List<Point2D> planeCoordinates) {
        alignmentCoordinatesArray(planeCoordinates);
    }

    public void printPlaneCoordinates() {
        System.out.println("Points...");

        for (int k = 0; k < planeCoordinates.length; k++) {
            System.out.println("p = " + planeCoordinates[k]);
        }
    }

    public Point2D[] getPlaneCoordinates() {
        return this.planeCoordinates;
    }

    public int getCoordinatesLimit() {
        return COORDINATES_LIMIT;
    }
}
