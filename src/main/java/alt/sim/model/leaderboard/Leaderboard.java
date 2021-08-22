package alt.sim.model.leaderboard;

import javafx.scene.control.TextField;

import java.util.List;

public interface Leaderboard {

    /**
     * Builds leaderboard using names and scores.
     * @param textFieldsNames of the users
     * @param textFieldsScores of the users
     */
    void buildLeaderboard(List<TextField> textFieldsNames, List<TextField> textFieldsScores);
}
