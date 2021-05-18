package alt.sim.view.leaderboard;

import alt.sim.view.CommonView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class LeaderboardView {


    @FXML
    public void initialize() {

    }

    @FXML
    public void onGoBackClick(final ActionEvent event) {
        CommonView.goBack();
    }
}
