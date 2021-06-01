package alt.sim.controller.leaderboard;

import java.util.List;
import java.util.Map;

public interface LeaderboardController {

    /**
     * Gets top five users name.
     * @return top five
     */
    List<String> getTopFive();

    /**
     * Gets all the users along with their scores.
     * @return map containing users and their scores
     */
    Map<String, Integer> getUsers();
}
