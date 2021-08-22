package alt.sim.model.leaderboard;
import alt.sim.model.user.records.UserRecordsImpl;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeaderboardImpl implements Leaderboard {

    private static final int TOP_FIVE = 5;

    /**
     * Gets top five users comparing their score.
     * @return top five users name list
     */
    private List<String> getTopFive() {
        return new UserRecordsImpl().getUsers().entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(TOP_FIVE)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public void buildLeaderboard(final List<TextField> textFieldsNames, final List<TextField> textFieldsScores) {
        List<String> names = getTopFive();
        Map<String, Integer> users = new UserRecordsImpl().getUsers();

        IntStream.range(0, names.size()).forEach(i -> {
            textFieldsNames.get(i).setText(names.get(i));
            textFieldsScores.get(i).setText(users.get(names.get(i)).toString());
        });
    }
}
