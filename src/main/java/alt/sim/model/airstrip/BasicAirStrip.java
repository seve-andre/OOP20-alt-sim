package alt.sim.model.airstrip;

import javafx.geometry.Dimension2D;

public class BasicAirStrip implements AirStrip {

    private final AirStripType type;

    public BasicAirStrip(final AirStripType type) {
        this.type = type;
    }

    @Override
    public Dimension2D getLandSpot() {
        //TO DO: calculate the spot
        //return new Dimension2DImpl(,);
        return null;
    }
}
