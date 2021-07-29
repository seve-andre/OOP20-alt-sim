package alt.sim.view;

import alt.sim.view.common.CommonView;
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
        Seaside.getParallelTransition().play();
    }

    @FXML
    public void onQuitClick() {
        CommonView.close();
    }
}
