package alt.sim.model.leaderboard;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import alt.sim.model.user.records.UserRecordsImpl;

public final class Leaderboard {

    private static final int TOP_FIVE = 5;

    private Leaderboard() { }

    /**
     * Gets top five users comparing their score.
     * @return top five users name list
     */
    public static List<String> getTopFive() {
        final List<String> topFiveUsers = new UserRecordsImpl().getUsers().entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(TOP_FIVE)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

        return topFiveUsers;
    }
}
