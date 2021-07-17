package alt.sim.model.airstrip;

import alt.sim.model.plane.Plane;
import alt.sim.model.user.User;
import javafx.scene.shape.Rectangle;

/**
 * This interface manage main methods for all airstrips.
 */
public interface AirStrip {

    /**
     * Method that when a plane is on landing spot, lands him.
     * @param plane: the plane that should land
     */
    void acceptPlane(Plane plane);

    /**
     * Method that calculates the area which plane should land.
     * @return the area planes can land on
     */
    Rectangle getLandSpot();

    /**
     * Method that set the score when a plane lands.
     * @param user the user who is playing
     * @param score the score made when a plane is landed
     */
    void setScore(User user, int score);
}
