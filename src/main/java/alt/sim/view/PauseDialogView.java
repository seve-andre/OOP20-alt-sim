package alt.sim.view;

import alt.sim.controller.game.GameController;
import alt.sim.view.common.CommonView;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PauseDialogView {

    @FXML
    public void initialize() { }

    @FXML
    public void onResumeClick(final ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        if (Seaside.getParallelTransition().getStatus().equals(Animation.Status.PAUSED)) {
            Seaside.getParallelTransition().play();
        }
        GameController.resume();
    }

    @FXML
    public void onQuitClick() {
        CommonView.close();
    }
}
