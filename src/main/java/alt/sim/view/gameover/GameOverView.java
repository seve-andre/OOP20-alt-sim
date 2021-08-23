package alt.sim.view.gameover;

import alt.sim.view.common.WindowView;
import javafx.fxml.FXML;

public class GameOverView {

    @FXML
    public void onQuitClick() {
        WindowView.close();
    }
}
