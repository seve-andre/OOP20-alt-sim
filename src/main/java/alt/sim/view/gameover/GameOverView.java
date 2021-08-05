package alt.sim.view.gameover;

import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOverView {

    @FXML
    public void initialize() { }

    @FXML
    public void onHomeClick(final ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        PageLoader.loadPage(Page.HOME);
    }

    @FXML
    public void onQuitClick() {
        CommonView.close();
    }
}
