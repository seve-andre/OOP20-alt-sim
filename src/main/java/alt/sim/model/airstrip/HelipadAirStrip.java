package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;

/**
 * This class represents the helipad airstrip where only helipads can land on it.
 */
public class HelipadAirStrip extends AbstractAirStrip {

    public HelipadAirStrip(final String url) {
        super(url);
    }

    @Override
    public boolean acceptPlane(final Plane helicopter) {
        //Not implemented because helicopters don't exist!
        return false;
    }

    @Override
    public String toString() {
        return "HelipadAirStrip [getStatus()=" + getStatus() + "]";
    }
}
