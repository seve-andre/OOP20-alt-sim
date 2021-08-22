package alt.sim.controller.leaderboard;

import alt.sim.model.leaderboard.LeaderboardImpl;
import javafx.scene.control.TextField;

import java.util.List;

public class LeaderboardControllerImpl implements LeaderboardController {

    private LeaderboardImpl leaderboard = new LeaderboardImpl();

    /**
     * {@inheritDoc}
     */
    public void buildLeaderboard(final List<TextField> names, final List<TextField> scores) {
        leaderboard.buildLeaderboard(names, scores);
    }
}
