package alt.sim.model.airstrip;

import javafx.geometry.Dimension2D;

public interface AirStrip {

    //TO DO: void acceptPlane(Plane plane);

    /**
     *
     * @return the area witch planes can land
     */
    Dimension2D getLandSpot();
}
