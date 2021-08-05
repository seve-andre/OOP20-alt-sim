package alt.sim.view.pause;

import alt.sim.controller.game.GameController;
import alt.sim.view.common.CommonView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PauseDialogView {

    // get reference from GameController
    private GameController gameController;

    @FXML
    public void initialize() { }

    @FXML
    public void onResumeClick(final ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
        gameController.resume();
    }

    @FXML
    public void onQuitClick() {
        CommonView.close();
    }
}
