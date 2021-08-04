package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the helipad airstrip where only helipads can land on it.
 */
public class HelipadAirStrip extends AbstractAirStrip {

    public HelipadAirStrip(final String url) {
        super(url);
    }

    @Override
    public Rectangle getLandSpot() {
        // TODO calculate land spot
        return null;
    }

    @Override
    public boolean acceptPlane(final Plane plane) {
        //TODO enable plane acception
        return false;
    }

    @Override
    public String toString() {
        return "HelipadAirStrip [getStatus()=" + getStatus() + "]";
    }
}
