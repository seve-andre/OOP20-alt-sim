package alt.sim.model.airstrip;

import alt.sim.model.user.User;
import javafx.scene.shape.Rectangle;

/**
 * This interface manage main methods for all airstrips.
 */
public interface AirStrip {

    /**
     * Give the landing spot as a rectangle.
     * @return a Rectangle box
     */
    Rectangle getBox();

    /**
     * Method that set the score when a plane lands.
     * @param user the user who is playing
     * @param score the score made when a plane is landed
     */
    void setScore(User user, int score);
}
