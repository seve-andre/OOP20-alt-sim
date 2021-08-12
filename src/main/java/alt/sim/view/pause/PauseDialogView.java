package alt.sim.view.pause;

import alt.sim.controller.game.GameController;
import alt.sim.view.common.WindowView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PauseDialogView {

    @FXML
    public void onResumeClick(final ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
        GameController.resume();
    }

    @FXML
    public void onQuitClick() {
        WindowView.close();
    }
}
