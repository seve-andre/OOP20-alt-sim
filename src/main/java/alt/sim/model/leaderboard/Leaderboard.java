package alt.sim.model.leaderboard;
import alt.sim.model.user.records.UserRecordsImpl;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Leaderboard {

    private static final int TOP_FIVE = 5;

    private Leaderboard() { }

    /**
     * Gets top five users comparing their score.
     * @return top five users name list
     */
    public static List<String> getTopFive() {
        return new UserRecordsImpl().getUsers().entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(TOP_FIVE)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
    }

    /**
     * Builds leaderboard using names and scores.
     * @param textFieldsNames of the users
     * @param textFieldsScores of the users
     */
    public static void buildLeaderboard(final List<TextField> textFieldsNames, final List<TextField> textFieldsScores) {
        List<String> names = getTopFive();
        Map<String, Integer> users = new UserRecordsImpl().getUsers();

        for (int i = 0; i < names.size(); i++) {
            textFieldsNames.get(i).setText(names.get(i));
            textFieldsScores.get(i).setText(users.get(names.get(i)).toString());
        }
    }
}
