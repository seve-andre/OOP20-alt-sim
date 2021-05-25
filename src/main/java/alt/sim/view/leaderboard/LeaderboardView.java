package alt.sim.view.leaderboard;

import java.util.List;

import alt.sim.model.leaderboard.Leaderboard;
import alt.sim.view.CommonView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class LeaderboardView {

    @FXML
    private TextField nameFirstPlace = new TextField();
    @FXML
    private TextField nameSecondPlace = new TextField();
    @FXML
    private TextField nameThirdPlace = new TextField();
    @FXML
    private TextField nameFourthPlace = new TextField();
    @FXML
    private TextField nameFifthPlace = new TextField();

    @FXML
    private TextField pointsFirstPlace = new TextField();
    @FXML
    private TextField pointsSecondPlace = new TextField();
    @FXML
    private TextField pointsThirdPlace = new TextField();
    @FXML
    private TextField pointsFourthPlace = new TextField();
    @FXML
    private TextField pointsFifthPlace = new TextField();

    private final List<String> names = Leaderboard.getTopFive();

    @FXML
    public void initialize() {
        try {
            nameFirstPlace.setText(names.get(0));
            nameSecondPlace.setText(names.get(1));
            nameThirdPlace.setText(names.get(2));
            nameFourthPlace.setText(names.get(3));
            nameFifthPlace.setText(names.get(4));
        } catch (final IndexOutOfBoundsException e) {

        }
    }

    @FXML
    public void onGoBackClick(final ActionEvent event) {
        CommonView.goBack();
    }
}
