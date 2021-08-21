package alt.sim.controller.leaderboard;

import alt.sim.model.leaderboard.Leaderboard;
import alt.sim.model.user.records.UserRecordsImpl;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

public class LeaderboardControllerImpl implements LeaderboardController {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTopFive() {
        return Leaderboard.getTopFive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getUsers() {
        return new UserRecordsImpl().getUsers();
    }

    public void buildLeaderboard(final List<TextField> names, final List<TextField> scores) {
        Leaderboard.buildLeaderboard(names, scores);
    }
}
