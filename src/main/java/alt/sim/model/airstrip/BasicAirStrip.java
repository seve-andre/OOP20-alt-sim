package alt.sim.model.airstrip;

import javafx.scene.shape.Rectangle;

/**
 * This class represents the standard airstrip which accepts classic planes.
 */
public class BasicAirStrip extends AbstractAirStrip {
    private Rectangle landingBox;

    public BasicAirStrip(final String url) {
        super(url);
    }

    /**
     * Getter method for landing spot.
     * @return the rectangle where planes should land
     */
    @Override
    public Rectangle getBox() {
        return this.landingBox;
    }

    /**
     * Setter method for landing spot.
     * @param box: the rectangle where planes should land
     */
    public void setBox(final Rectangle box) {
        this.landingBox = box;
    }

    @Override
    public String toString() {
        return "BasicAirStrip [status=" + this.getStatus() + "]";
    }
}
