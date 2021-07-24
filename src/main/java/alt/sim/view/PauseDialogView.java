package alt.sim.view;

import alt.sim.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PauseDialogView {

    @FXML
    public void initialize() {
    }

    @FXML
    public void onResumeClick(final ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void onQuitClick(final ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        Main.getStage().close();
        Platform.exit();
        System.exit(0);
    }
}
