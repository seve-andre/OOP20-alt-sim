package alt.sim.view.home;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeView {

    @FXML
    private Button startBtn;
    @FXML
    private Button leaderboardBtn;
    @FXML
    private Button creditsBtn;
    @FXML
    private Button exitBtn;

    private final PageLoader pageLoader = new PageLoader();

    @FXML
    public void onStartClick(final ActionEvent event) {
        pageLoader.loadPage(Main.getStage(), Page.MAP_CHOICE);
    }
    @FXML
    public void onLeaderboardClick(final ActionEvent event) {
        pageLoader.loadPage(Main.getStage(), Page.LEADERBOARD);
    }
    @FXML
    public void onCreditsClick(final ActionEvent event) {
        pageLoader.loadPage(Main.getStage(), Page.CREDITS);
    }
    @FXML
    public void onExitClick(final ActionEvent event) {
        Platform.exit();
    }

}
