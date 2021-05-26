package alt.sim.view.mapchoice;

import alt.sim.Main;
import alt.sim.model.user.validation.NameQuality;
import alt.sim.model.user.validation.NameValidation;
import alt.sim.view.CommonView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MapChoiceView {

    @FXML
    private TextField nameTextField = new TextField();
    @FXML
    private final Button seasideBtn = new Button();
    @FXML
    private final Button riversideBtn = new Button();
    @FXML
    private final Button citysideBtn = new Button();
    @FXML
    private final Button countrysideBtn = new Button();
    @FXML
    private Button playBtn = new Button();
    @FXML
    private TextField infoTextField = new TextField();

    private GameMap mapToPlay = GameMap.getRandomMap();

    @FXML
    public void initialize() {
    }

    @FXML
    public void onGoBackClick(final ActionEvent event) {
        CommonView.goBack();
    }

    @FXML
    public void onNameEnter(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            final NameValidation result = new NameQuality().checkName(nameTextField.getText());
            if (!result.equals(NameValidation.CORRECT)) {
                infoTextField.setText("Name is " + result.getResult());
            } else {
                infoTextField.setText("");
            }
        }
    }

    @FXML
    public void onPlayClick(final ActionEvent event) {
        new PageLoader().loadPage(Main.getStage(), Page.GAME, this.mapToPlay);
    }

    @FXML
    public void onSeasideClick(final ActionEvent event) {
        this.playBtn.setText("PLAY");
        this.mapToPlay = GameMap.SEASIDE;
    }

    @FXML
    public void onRiversideClick(final ActionEvent event) {
        this.playBtn.setText("PLAY");
        this.mapToPlay = GameMap.RIVERSIDE;
    }
    @FXML
    public void onCitysideClick(final ActionEvent event) {
        this.playBtn.setText("PLAY");
        this.mapToPlay = GameMap.CITYSIDE;
    }
    @FXML
    public void onCountrysideClick(final ActionEvent event) {
        this.playBtn.setText("PLAY");
        this.mapToPlay = GameMap.COUNTRYSIDE;
    }

}
