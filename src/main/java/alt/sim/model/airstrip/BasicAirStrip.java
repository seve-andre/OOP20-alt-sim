package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import alt.sim.model.plane.State;
import alt.sim.view.seaside.Seaside;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {
    private Rectangle landingBox;
    private Seaside transitionSeaside;

    public BasicAirStrip(final String url) {
        super(url);
    }

    public BasicAirStrip(final String url, final Seaside transitionSeaside) {
        super(url, transitionSeaside);
        this.transitionSeaside = transitionSeaside;
    }

    @Override
    public boolean acceptPlane(final Plane plane) {
        if (checkCollision(plane) && !plane.isLanded()) {
            plane.setState(State.LANDED);
            super.setStatus(AirStripStatus.BUSY);
            plane.getLandingAnimation().play();
            plane.removedObservableStateListener();
            System.out.println("Plane" + plane.hashCode() + " landing...");
            plane.getLandingAnimation().setOnFinished(finish -> transitionSeaside.removePlane(plane));
            return true;
        }
        super.setStatus(AirStripStatus.FREE);
        return false;
    }

    /**
     * private method for collision between planes and strip.
     * @param plane: the plane which should land
     * @return true if the plane landed, false otherwise
     */
    private boolean checkCollision(final Plane plane) {
        Bounds monitoredPlaneBounds = plane.getSprite().getBoundsInParent();

        return monitoredPlaneBounds.intersects(landingBox.getBoundsInParent());
    }

    /**
     * Setter method for left landing spot.
     * @param box: the rectangle where planes should land
     */
    public void setBox(final Rectangle box) {
        this.landingBox = box;
    }

    @Override
    public String toString() {
        return "BasicAirStrip [status=" + this.getStatus() + "]";
    }
}
