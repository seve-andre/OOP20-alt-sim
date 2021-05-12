package alt.sim.model.airstrip;
import alt.sim.model.calculation.Dimension2D;
import javafx.scene.image.ImageView;

public interface AirStrip {

    //TO DO: void acceptPlane(Plane plane);

    /**
     *
     * @return the area witch planes can land
     */
    Dimension2D getLandSpot();

    /**
     *
     * @param view the graphical representation of the AirStrip
     */
    void setAirStripImage(ImageView view);
}
