package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {
    private Rectangle landingBoxLeft;
    private Rectangle landingBoxRight;

    public BasicAirStrip(final String url) {
        super(url);
    }

    @Override
    public Rectangle getLandSpot() {
        // TODO calculate land spot
        return null;
    }

    @Override
    public void acceptPlane(final Plane plane) {
        if (checkCollision(plane)) {
            super.setStatus(AirStripStatus.BUSY);
            plane.getLandingAnimation().play();
            //TODO: super.setScore(UserImpl.getUser(), 50);
            System.out.println("Plane landing...");
        }
        super.setStatus(AirStripStatus.FREE);
    }

    private boolean checkCollision(final Plane plane) {
        Bounds monitoredPlaneBounds = plane.getImagePlane().getBoundsInParent();

        return monitoredPlaneBounds.intersects(landingBoxLeft.getBoundsInParent())
                || monitoredPlaneBounds.intersects(landingBoxRight.getBoundsInParent());
    }
    /**
     * Setter method for left landing spot.
     * @param leftBox: the left rectangle
     */
    public void setBoxLeft(final Rectangle leftBox) {
        this.landingBoxLeft = leftBox;
    }
    /**
     * Setter method for right landing spot.
     * @param rightBox: the right rectangle
     */
    public void setBoxRight(final Rectangle rightBox) {
        this.landingBoxRight = rightBox;
    }

    @Override
    public String toString() {
        return "BasicAirStrip [status=" + this.getStatus() + "]";
    }
}
