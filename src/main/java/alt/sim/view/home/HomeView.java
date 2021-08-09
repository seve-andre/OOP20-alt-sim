package alt.sim.view.home;

import alt.sim.view.common.WindowView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.fxml.FXML;

public class HomeView {

    @FXML
    public void onStartClick() {
        PageLoader.loadPage(Page.MAP_CHOICE);
    }

    @FXML
    public void onLeaderboardClick() {
        PageLoader.loadPage(Page.LEADERBOARD);
    }

    @FXML
    public void onCreditsClick() {
        PageLoader.loadPage(Page.CREDITS);
    }

    @FXML
    public void onExitClick() {
        WindowView.close();
    }

    @FXML
    public void onMinimizeClick() {
        WindowView.minimize();
    }

    @FXML
    public void onCloseClick() {
        WindowView.close();
    }
}
