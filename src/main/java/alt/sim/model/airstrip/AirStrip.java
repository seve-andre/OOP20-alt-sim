package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import javafx.geometry.Dimension2D;

/**
 * This interface manage main methods for all airstrips.
 */
public interface AirStrip {

    void acceptPlane(Plane plane);

    /**
     *
     * @return the area planes can land on
     */
    Dimension2D getLandSpot();
}
