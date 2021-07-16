package alt.sim.view;

import java.io.IOException;

import alt.sim.Main;
import alt.sim.controller.MapController;
import alt.sim.model.user.records.UserRecordsImpl;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOverView {

    private UserRecordsImpl userRecords = new UserRecordsImpl();

    @FXML
    public void initialize() { }

    @FXML
    public void onHomeClick(final ActionEvent event) throws IOException {
        this.userRecords.updateScore(MapController.getName());
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        new PageLoader().loadPage(Page.HOME);
    }

    @FXML
    public void onQuitClick(final ActionEvent event) throws IOException {
        this.userRecords.updateScore(MapController.getName());
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        Main.getStage().close();
    }
}
