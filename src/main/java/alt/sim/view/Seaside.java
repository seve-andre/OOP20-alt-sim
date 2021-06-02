package alt.sim.view;

import java.io.IOException;

import alt.sim.controller.MapController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Seaside {

    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField score = new TextField();
    private static final Stage POPUP_STAGE = new Stage(StageStyle.UNDECORATED);
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        score.setText(String.valueOf(Integer.parseInt(score.getText()) + 10));
    }));

    @FXML
    public void initialize() {
        name.setText(MapController.getName());
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onPauseClick() throws IOException {
        timeline.pause();
        CommonView.onPauseClick();
        timeline.play();
    }
}
