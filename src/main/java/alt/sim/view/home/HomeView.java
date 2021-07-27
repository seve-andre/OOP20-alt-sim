package alt.sim.view.home;

import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeView {

    @FXML
    public void onStartClick(final ActionEvent event) {
        PageLoader.loadPage(Page.MAP_CHOICE);
    }
    
    @FXML
    public void onLeaderboardClick(final ActionEvent event) {
        PageLoader.loadPage(Page.LEADERBOARD);
    }
    
    @FXML
    public void onCreditsClick(final ActionEvent event) {
        PageLoader.loadPage(Page.CREDITS);
    }
    
    @FXML
    public void onExitClick(final ActionEvent event) {
        CommonView.close();
    }

    @FXML
    public void onMinimizeClick(final ActionEvent event) {
        CommonView.minimize();
    }

    @FXML
    public void onCloseClick(final ActionEvent event) {
        CommonView.close();
    }
}
