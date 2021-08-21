package alt.sim.model.airstrip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import alt.sim.model.user.User;
import alt.sim.model.user.UserImpl;

public class AirStripScoreTest {

    private static final int STANDARD_SCORE = 50;
    private static final int INVALID_SCORE = -100;

    private AirStrip strip1 = new BasicAirStrip();
    private AirStrip strip2 = new HelipadAirStrip();
    private User user1 = new UserImpl("Andrea", 0);
    private User user2 = new UserImpl("Daniel", 0);

    @Test
    public void checkScore() {
        strip1.setScore(user1, STANDARD_SCORE);
        strip2.setScore(user2, STANDARD_SCORE * 2);
        user2.resetScore();
        assertEquals(user1.getScore(), STANDARD_SCORE);
        assertEquals(user2.getScore(), 0);
        strip2.setScore(user2, STANDARD_SCORE);
        strip2.setScore(user2, INVALID_SCORE);
        assertNotEquals(user2.getScore(), STANDARD_SCORE - INVALID_SCORE);
        assertEquals(user2.getScore(), STANDARD_SCORE);
    }
}
