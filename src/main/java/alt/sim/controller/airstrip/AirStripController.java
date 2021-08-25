package alt.sim.controller.airstrip;

import alt.sim.controller.seaside.SeasideController;
import alt.sim.model.airstrip.AbstractAirStrip;
import alt.sim.model.airstrip.AirStripStatus;
import alt.sim.model.plane.PlaneImpl;
import alt.sim.model.plane.State;
import javafx.geometry.Bounds;

public final class AirStripController {

    private AirStripController() { }

    /**
     * Try a plane to land on a strip.
     * @param strip: the strip where should land the plane
     * @param transitionSeaside: the controller of the seaside map
     * @param plane: the plane which should land on the strip
     * @return true if plane landed, false otherwise
     */
    public static boolean acceptPlane(final AbstractAirStrip strip, final SeasideController transitionSeaside,
            final PlaneImpl plane) {

        if (strip.getStatus().equals(AirStripStatus.DISABLED) || strip.getStatus().equals(AirStripStatus.BUSY)) {
            return false;
        } else if (checkCollision(strip, plane) && !plane.isLanded() && strip.getStatus().equals(AirStripStatus.FREE)) {
            plane.setState(State.LANDED);
            strip.setStatus(AirStripStatus.BUSY);
            plane.getLandingAnimation().play();
            plane.removedObservableStateListener();
            clearMap(plane, transitionSeaside);
            strip.setStatus(AirStripStatus.FREE);
            return true;
        }
        return false;
    }

    /**
     * Give a notify if a collision between a strip and plane is detected.
     * @param strip: the strip interested
     * @param plane: the plane interested
     * @return true if the collision is dected, false otherwise
     */
    private static boolean checkCollision(final AbstractAirStrip strip, final PlaneImpl plane) {
        Bounds monitoredPlaneBounds = plane.getSprite().getBoundsInParent();
        return monitoredPlaneBounds.intersects(strip.getBox().getBoundsInParent());
    }

    /**
     * Clears lines and plane when a plane is correctly landed.
     * @param plane: the plane that has landed
     * @param transitionSeaside: the controller of the seaside map
     */
    private static void clearMap(final PlaneImpl plane, final SeasideController transitionSeaside) {
        plane.getLandingAnimation().setOnFinished(finish -> {
            transitionSeaside.removePlane(plane);
            transitionSeaside.clearLinesDrawed();
            transitionSeaside.restoreLinesRemoved();
        });
    }

}
