package alt.sim.controller.leaderboard;

import java.util.List;
import java.util.Map;

import alt.sim.model.leaderboard.Leaderboard;
import alt.sim.model.user.records.UserRecordsImpl;

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

}
