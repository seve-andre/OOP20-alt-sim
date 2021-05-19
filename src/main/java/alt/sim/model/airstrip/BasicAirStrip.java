package alt.sim.model.airstrip;

import alt.sim.model.calculation.Sprite;
import alt.sim.model.plane.Plane;
import javafx.geometry.Dimension2D;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {

    public BasicAirStrip() {
        super();
        Sprite.setURLSprite("images/airstrip.png");
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
