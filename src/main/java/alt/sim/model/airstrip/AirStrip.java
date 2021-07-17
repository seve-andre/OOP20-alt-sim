package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import alt.sim.model.user.User;
import javafx.scene.shape.Rectangle;

/**
 * This interface manage main methods for all airstrips.
 */
public interface AirStrip {

    void acceptPlane(Plane plane);

    /**
     *
     * @return the area planes can land on
     */
    Rectangle getLandSpot();

    /**
     *
     * @param user the user who is playing
     * @param score the score made when a plane is landed
     */
    void setScore(User user, int score);
}
