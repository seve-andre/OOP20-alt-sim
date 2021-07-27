package alt.sim.view.leaderboard;

import alt.sim.controller.leaderboard.LeaderboardControllerImpl;
import alt.sim.model.user.records.RecordsFolder;
import alt.sim.view.common.CommonView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Sets up leaderboard view with names and points.
 */
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

    private final List<String> names = new LeaderboardControllerImpl().getTopFive();
    private final Map<String, Integer> users = new LeaderboardControllerImpl().getUsers();

    @FXML
    public void initialize() {
        try {
            nameFirstPlace.setText(names.get(0));
            pointsFirstPlace.setText(users.get(names.get(0)).toString());

            nameSecondPlace.setText(names.get(1));
            pointsSecondPlace.setText(users.get(names.get(1)).toString());

            nameThirdPlace.setText(names.get(2));
            pointsThirdPlace.setText(users.get(names.get(2)).toString());

            nameFourthPlace.setText(names.get(3));
            pointsFourthPlace.setText(users.get(names.get(3)).toString());

            nameFifthPlace.setText(names.get(4));
            pointsFifthPlace.setText(users.get(names.get(4)).toString());
        } catch (final IndexOutOfBoundsException e) {
            // DO NOTHING HERE!
        }
    }

    @FXML
    public void onGoBackClick() {
        CommonView.goBack();
    }

    @FXML
    public void onGoToFileClick() throws IOException {
        Desktop.getDesktop().open(Paths.get(RecordsFolder.RecordsPath.USER_RECORDS_FILE_PATH.getPath()).toFile());
    }

    @FXML
    public void onMinimizeClick() {
        CommonView.minimize();
    }

    @FXML
    public void onCloseClick() {
        CommonView.close();
    }
}
