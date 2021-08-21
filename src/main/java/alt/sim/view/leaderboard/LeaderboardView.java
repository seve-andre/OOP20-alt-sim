package alt.sim.view.leaderboard;

import alt.sim.controller.leaderboard.LeaderboardControllerImpl;
import alt.sim.model.user.records.RecordsFolder;
import alt.sim.view.common.WindowView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Sets up leaderboard view with names and points.
 */
public class LeaderboardView {

    @FXML
    private TextField nameFirstPlace;
    @FXML
    private TextField nameSecondPlace;
    @FXML
    private TextField nameThirdPlace;
    @FXML
    private TextField nameFourthPlace;
    @FXML
    private TextField nameFifthPlace;

    @FXML
    private TextField pointsFirstPlace;
    @FXML
    private TextField pointsSecondPlace;
    @FXML
    private TextField pointsThirdPlace;
    @FXML
    private TextField pointsFourthPlace;
    @FXML
    private TextField pointsFifthPlace;

    @FXML
    public void initialize() {
        List<TextField> textFieldsNames = List.of(
                nameFirstPlace, nameSecondPlace, nameThirdPlace, nameFourthPlace, nameFifthPlace);
        List<TextField> textFieldsScores = List.of(
                pointsFirstPlace, pointsSecondPlace, pointsThirdPlace, pointsFourthPlace, pointsFifthPlace);

        new LeaderboardControllerImpl().buildLeaderboard(textFieldsNames, textFieldsScores);
    }

    @FXML
    public void onGoBackClick() {
        WindowView.goBack();
    }

    @FXML
    public void onGoToFileClick() throws IOException {
        openFile();
    }

    @FXML
    public void onMinimizeClick() {
        WindowView.minimize();
    }

    @FXML
    public void onCloseClick() {
        WindowView.close();
    }

    /**
     * Opens file containing users.
     * @throws IOException if path is not correct
     */
    private void openFile() throws IOException {
        Desktop.getDesktop().open(Paths.get(RecordsFolder.RecordsPath.USER_RECORDS_FILE_PATH.getPath()).toFile());
    }
}
