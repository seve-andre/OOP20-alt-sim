package alt.sim.controller.leaderboard;

import alt.sim.model.leaderboard.Leaderboard;
import javafx.scene.control.TextField;

import java.util.List;

public class LeaderboardControllerImpl implements LeaderboardController {

    /**
     * {@inheritDoc}
     */
    public void buildLeaderboard(final List<TextField> names, final List<TextField> scores) {
        Leaderboard.buildLeaderboard(names, scores);
    }
}
