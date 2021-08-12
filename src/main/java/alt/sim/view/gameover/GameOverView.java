package alt.sim.view.gameover;

import alt.sim.view.common.WindowView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameOverView {

    @FXML
    public void onHomeClick(final ActionEvent event) {
        PageLoader.loadPage(Page.HOME);
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void onQuitClick() {
        WindowView.close();
    }
}
