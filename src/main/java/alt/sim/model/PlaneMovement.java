package alt.sim.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

public class PlaneMovement {
    /** MAX-Range of samping coordinates. */
    public static final int COORDINATES_LIMIT = 200;

    //private Point2D[] planeCoordinates;
    private List<Point2D> planeCoordinatesList;

    public PlaneMovement() {
        //this.planeCoordinates = new Point2D[COORDINATES_LIMIT];
        this.planeCoordinatesList = new ArrayList<>();
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

    public List<Point2D> alignmentCoordinatesArrayList(final List<Point2D> planeCoordinates) {
        this.planeCoordinatesList = planeCoordinates;

        return this.planeCoordinatesList;
    }


    /*
     * public void setPlaneCoordinates(final List<Point2D> planeCoordinates) {
     * alignmentCoordinatesArray(planeCoordinates); }
     */

    public void setPlaneCoordinatesList(final List<Point2D> planeCoordinates) {
        alignmentCoordinatesArrayList(planeCoordinates);
    }

    public void printPlaneCoordinates() {
        System.out.println("Points...");

        /*
         * for (int k = 0; k < planeCoordinates.length; k++) { System.out.println("p = "
         * + planeCoordinates[k]); }
         */

        for (Point2D point:planeCoordinatesList) {
            System.out.println("p = "+ point);
        }
    }

    /*
     * public Point2D[] getPlaneCoordinates() {
     * //System.out.println("getPlaneCoordinates.lenght() = " +
     * planeCoordinates.length); return this.planeCoordinates; }
     */

    public List<Point2D> getPlaneCoordinatesList() {
        return this.planeCoordinatesList;
    }

    public int getCoordinatesLimit() {
        return COORDINATES_LIMIT;
    }
}
