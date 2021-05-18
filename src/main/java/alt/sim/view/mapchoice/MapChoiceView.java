package alt.sim.view.mapchoice;

import alt.sim.view.CommonView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class MapChoiceView {

    @FXML
    private TextField nameTextField;

    @FXML
    public void initialize() {

    }

    @FXML
    public void onGoBackClick(final ActionEvent event) {
        CommonView.goBack();
    }

    @FXML
    public void onNameEnter(final ActionEvent event) {
    }

    @FXML
    public void onMapClick(final ActionEvent event) {
    }

    @FXML
    public void onPlayClick(final ActionEvent event) {
    }
}

