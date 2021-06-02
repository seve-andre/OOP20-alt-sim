package alt.sim.view;

import java.io.IOException;
import java.util.LinkedHashMap;

import alt.sim.model.user.records.UserRecordsImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Seaside {

    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField score = new TextField();
    private UserRecordsImpl userRecords = new UserRecordsImpl();

    @FXML
    public void initialize() throws IOException {
        final LinkedHashMap<String, Integer> users = this.userRecords.getUsers();
        name.setText((String) users.keySet().toArray()[users.size() - 1]);
    }

    @FXML
    public void onPauseClick() throws IOException {
        CommonView.onPauseClick();
    }
}
