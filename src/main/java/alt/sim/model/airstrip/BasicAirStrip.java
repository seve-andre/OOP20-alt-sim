package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import javafx.geometry.Dimension2D;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {

    private static final String IMAGE_URL = "images/map_components/airstrip.png";

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
        return "BasicAirStrip [status=" + this.getStatus() + "]";
    }
}
