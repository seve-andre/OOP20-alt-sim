package alt.sim.controller.leaderboard;

import javafx.scene.control.TextField;

import java.util.List;

public interface LeaderboardController {

    /**
     * Builds leaderboard comparing users.
     * @param names of the users
     * @param scores of the users
     */
    void buildLeaderboard(List<TextField> names, List<TextField> scores);
}
