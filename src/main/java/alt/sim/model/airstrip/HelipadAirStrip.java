package alt.sim.model.airstrip;

import javafx.scene.shape.Rectangle;

/**
 * This class represents the helipad airstrip where only helicopters can land on it.
 */
public class HelipadAirStrip extends AbstractAirStrip {

    public HelipadAirStrip(final String url) {
        super(url);
    }

    @Override
    public String toString() {
        return "HelipadAirStrip [getStatus()=" + getStatus() + "]";
    }

    @Override
    public Rectangle getBox() {
        //Helicopters not implemented, so helipad functions are disabled..
        return null;
    }
}
