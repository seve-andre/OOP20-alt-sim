package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import javafx.geometry.Dimension2D;

/**
 * This class represents the helipad airstrip where only helipads can land on it.
 */
public class HelipadAirStrip extends AbstractAirStrip {

    private static final String IMAGE_URL = "images/map_components/helipad.png";

    public void loadImage() {
        super.loadImage(IMAGE_URL);
    }

    @Override
    public Dimension2D getLandSpot() {
        // TODO calculate land spot
        return null;
    }

    @Override
    public void acceptPlane(final Plane plane) {
        //TODO enable plane acception
    }

    @Override
    public String toString() {
        return "HelipadAirStrip [getStatus()=" + getStatus() + "]";
    }
}
