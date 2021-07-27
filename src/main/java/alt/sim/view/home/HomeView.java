package alt.sim.view.home;

import alt.sim.view.common.CommonView;
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
        CommonView.close();
    }

    @FXML
    public void onMinimizeClick() {
        CommonView.minimize();
    }

    @FXML
    public void onCloseClick() {
        CommonView.close();
    }
}
