package alt.sim.view.mapchoice;

import alt.sim.controller.mapchoice.MapChoiceControllerImpl;
import alt.sim.model.user.validation.NameValidation;
import alt.sim.view.common.CommonView;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.IOException;


public class MapChoiceView {

    @FXML
    private TextField nameTextField = new TextField();
    @FXML
    private Button playBtn = new Button();
    @FXML
    private TextField infoTextField = new TextField();
    @FXML
    private Tooltip infoTooltip = new Tooltip();

    //.getRandomMap(); should be used when all maps fxml are done
    private GameMap mapToPlay = GameMap.SEASIDE;
    private final MapChoiceControllerImpl mapChoiceController = new MapChoiceControllerImpl();

    @FXML
    public void initialize() {
        infoTooltip.setShowDelay(Duration.millis(100));
    }

    @FXML
    public void onGoBackClick() {
        CommonView.goBack();
    }

    /**
     * If ENTER key is pressed, name quality will be checked.
     * @param event enter key to invoke method
     * @throws IOException
     */
    @FXML
    public void onNameEnter(final KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            final NameValidation result = mapChoiceController.checkName(nameTextField.getText());
            if (result.equals(NameValidation.CORRECT)) {
                infoTextField.setStyle("-fx-text-fill: #006500;");
            }
            infoTextField.setText("NAME IS " + result.getResult().toUpperCase() + "!");
        }
    }

    /**
     * Loads GameMap fxml when button is clicked.
     * @throws IOException
     */
    @FXML
    public void onPlayClick() throws IOException {
        final NameValidation result = mapChoiceController.checkName(nameTextField.getText());
        if (result.equals(NameValidation.CORRECT)) {
            mapChoiceController.addUser(nameTextField.getText());
            PageLoader.loadPage(Page.GAME, this.mapToPlay);
        } else {
            infoTextField.setText("NAME IS " + result.getResult().toUpperCase() + "!");
        }
    }

    @FXML
    public void onSeasideClick() {
        this.playBtn.setText("PLAY SEASIDE");
        this.mapToPlay = GameMap.SEASIDE;
    }

    @FXML
    public void onRiversideClick() {
        this.playBtn.setText("PLAY RIVERSIDE");
        this.mapToPlay = GameMap.RIVERSIDE;
    }

    @FXML
    public void onCitysideClick() {
        this.playBtn.setText("PLAY CITYSIDE");
        this.mapToPlay = GameMap.CITYSIDE;
    }

    @FXML
    public void onCountrysideClick() {
        this.playBtn.setText("PLAY COUNTRYSIDE");
        this.mapToPlay = GameMap.COUNTRYSIDE;
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
