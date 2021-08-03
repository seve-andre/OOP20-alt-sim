package alt.sim.view;

import alt.sim.controller.game.GameController;
import alt.sim.view.common.CommonView;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PauseDialogView {

    @FXML
    public void initialize() { }

    @FXML
    public void onResumeClick(final ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
        if (Seaside.getOnePlanePT().getStatus().equals(Animation.Status.PAUSED)) {
            Seaside.getOnePlanePT().play();
        }
        GameController.resume();
    }

    @FXML
    public void onQuitClick() {
        CommonView.close();
    }
}
