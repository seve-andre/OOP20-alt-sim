package alt.sim.model.plane;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class PlaneMovement {
    /** MAX-Range of samping coordinates. */
    public static final int COORDINATES_LIMIT = 200;

    /**
     *
     */
    public static final int MIN_COORDINATES_LENGTH = 5;
    /**
     *
     */
    public static final int MAX_DISTANCE_DRAWINGPATH_VALUE = 150;


    private List<Point2D> planeCoordinates;

    public PlaneMovement() {
        this.planeCoordinates = new ArrayList<>();
    }

    //Allying the dimension of array to dimension of planeCoordinates
    /*
     * private void alignmentCoordinatesArray(final List<Point2D> planeCoordinates)
     * { for (Point2D pointPassed:planeCoordinates) {
     * //System.out.println(pointPassed.getX() + " | " + pointPassed.getY()); }
     *
     * if (this.planeCoordinates.length > planeCoordinates.size()) {
     * this.planeCoordinates = new Point2D[planeCoordinates.size()];
     *
     * for (int j = 0; j < planeCoordinates.size(); j++) { if
     * (planeCoordinates.get(j) != null) { this.planeCoordinates[j] =
     * planeCoordinates.get(j); } } } else if (planeCoordinates.size() ==
     * COORDINATES_LIMIT) { this.planeCoordinates = (Point2D[])
     * Arrays.copyOf(planeCoordinates.toArray(), planeCoordinates.size()); } }
     */

    public List<Point2D> alignmentCoordinates(final List<Point2D> planeCoordinates) {
        this.planeCoordinates = planeCoordinates;

        return this.planeCoordinates;
    }


    /*
     * public void setPlaneCoordinates(final List<Point2D> planeCoordinates) {
     * alignmentCoordinatesArray(planeCoordinates); }
     */

    public void setPlaneCoordinates(final List<Point2D> planeCoordinates) {
        alignmentCoordinates(planeCoordinates);
    }

    public void printPlaneCoordinates() {
        System.out.println("Points...");

        /*
         * for (int k = 0; k < planeCoordinates.length; k++) { System.out.println("p = "
         * + planeCoordinates[k]); }
         */

        for (Point2D point:planeCoordinates) {
            System.out.println("p = " + point);
        }
    }

    /*
     * public Point2D[] getPlaneCoordinates() {
     * //System.out.println("getPlaneCoordinates.lenght() = " +
     * planeCoordinates.length); return this.planeCoordinates; }
     */

    public List<Point2D> getPlaneCoordinates() {
        return this.planeCoordinates;
    }

    public int getCoordinatesLimit() {
        return COORDINATES_LIMIT;
    }
}
