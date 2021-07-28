package alt.sim.view;

import alt.sim.Main;
import alt.sim.model.user.records.UserRecordsImpl;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOverView {

    private final UserRecordsImpl userRecords = new UserRecordsImpl();

    @FXML
    public void initialize() { }

    @FXML
    public void onHomeClick() {
        //this.userRecords.updateScore(MapController.getName(),0);
        //((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        PageLoader.loadPage(Page.HOME);
    }

    @FXML
    public void onQuitClick(final ActionEvent event) {
        //this.userRecords.updateScore(MapController.getName(),0);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        Main.getStage().close();
    }
}
