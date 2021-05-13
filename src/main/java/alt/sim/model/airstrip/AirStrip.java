package alt.sim.model.airstrip;

import javafx.geometry.Dimension2D;

public interface AirStrip {

    //TO DO: void acceptPlane(Plane plane);

    /**
     *
     * @return the area planes can land on
     */
    Dimension2D getLandSpot();
}
