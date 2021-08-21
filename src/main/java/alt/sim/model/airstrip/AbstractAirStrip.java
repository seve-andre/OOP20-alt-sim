package alt.sim.model.airstrip;

import alt.sim.model.user.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * This class is the middle between the interface and the specialized classes for the airstrips.
 *
 */
public abstract class AbstractAirStrip implements AirStrip {

    private AirStripStatus status;
    private ImageView imageAirstrip;

    /**
     * When an airstrip is created, is ready to accept planes.
     */
    public AbstractAirStrip(final String url) {
        this.imageAirstrip = new ImageView(new Image(url));
        this.status = AirStripStatus.FREE;
    }

    @Override
    public void setScore(final User user, final int score) {
        user.setScore(score);
    }
    /**
     * Getter method for the airstrip enum status property.
     * @return the s@Override
    tatus of the airstrip
     */
    public AirStripStatus getStatus() {
        return this.status;
    }

    /**
     * Setter method for the airstrip enum status property.
     * @param status: the status of the airstrip
     */
    public void setStatus(final AirStripStatus status) {
        this.status = status;
    }
    /**
     * Getter method for the airstrip image.
     * @return the airstrip image
     */
    public ImageView getAirStripImage() {
        return this.imageAirstrip;
    }
    /**
     * Setter method for the airstrip image.
     * @param image: the airstrip image
     */
    public void setAirStripImage(final ImageView image) {
        this.imageAirstrip = image;
    }

    @Override
    public abstract Rectangle getBox();
}
