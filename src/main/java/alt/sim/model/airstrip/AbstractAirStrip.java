package alt.sim.model.airstrip;

import alt.sim.model.calculation.Sprite;
import alt.sim.model.plane.Plane;
import alt.sim.model.user.UserImpl;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * This class is the middle between the interface and the specialized classes for the airstrips.
 *
 */
public abstract class AbstractAirStrip implements AirStrip {

    private AirStripStatus status;
    private Sprite airstripSprite;

    /**
     * When an airstrip is created, is ready to accept planes.
     */
    public AbstractAirStrip() {
        this.airstripSprite = new Sprite(new Point2D(0, 0));
        this.status = AirStripStatus.FREE;
    }

    @Override
    public abstract Dimension2D getLandSpot();

    @Override
    public abstract void acceptPlane(Plane plane);

    @Override
    public void setScore(final UserImpl user, final int score) {
        user.setScore(score);
    }
    /**
     * Getter method for the airstrip image property.
     * @return the image of the airstrip
     */
    public ImageView getAirStripImage() {
        return this.airstripSprite.getImage();
    }

    /**
     * Getter method for the airstrip enum status property.
     * @return the status of the airstrip
     */
    public AirStripStatus getStatus() {
        return this.status;
    }
}
