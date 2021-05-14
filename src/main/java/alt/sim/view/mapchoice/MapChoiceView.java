package alt.sim.view.mapchoice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapChoiceView {

    @FXML
    private Button nameTextField;

    @FXML
    public void initialize() {
        this.nameTextField.setText("ciao");
    }
}
