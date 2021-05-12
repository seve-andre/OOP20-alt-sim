package alt.sim.model.airstrip;

import alt.sim.model.calculation.Dimension2D;
import javafx.scene.image.ImageView;

public class BasicAirStrip implements AirStrip {

    private final double width;
    private final double heigth;
    private final AirStripType type;
    private ImageView image;

    public BasicAirStrip(final double width, final double heigth, final AirStripType type, final ImageView image) {
        this.width = width;
        this.heigth = heigth;
        this.type = type;
        this.image = image;
    }

    @Override
    public Dimension2D getLandSpot() {
        //TO DO: calculate the spot
        //return new Dimension2DImpl(,);
        return null;
    }

    @Override
    public void changeAirStripImage(final ImageView view) {
        this.image = view;
    }
}
