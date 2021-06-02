package alt.sim.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import alt.sim.model.plane.Plane;
import javafx.geometry.Point2D;

public class PlaneMovement {
    private static final int COORDINATES_LIMIT = 300;

    private Plane planeInGame;
    private Point2D[] planeCoordinates;
    private int positionArray;

    public PlaneMovement(final Plane planeInGame) {
        this.planeInGame = planeInGame;
        this.planeCoordinates = new Point2D[COORDINATES_LIMIT];
        this.positionArray = 0;
    }

    private void alignmentCoordinatesArray(final List<Point2D> planeCoordinates) {
        for (Point2D pointPassed:planeCoordinates) {
            System.out.println(pointPassed.getX() + " | " + pointPassed.getY());
        }

        if (this.planeCoordinates.length > planeCoordinates.size()) {
            this.planeCoordinates = Arrays.copyOf(new Point2D[planeCoordinates.size()], planeCoordinates.size());
        } else if (planeCoordinates.size() == COORDINATES_LIMIT) {
            this.planeCoordinates = (Point2D[]) Arrays.copyOf(planeCoordinates.toArray(), planeCoordinates.size());
        }
    }

    // Warning potential bug
    public void setPlaneCoordinates(final double x, final double y) {
        if (positionArray < COORDINATES_LIMIT) {
            this.planeCoordinates[this.positionArray++].add(x, y);
        } else {
            System.out.println("ARRAY LIMIT REACHED");
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
}
