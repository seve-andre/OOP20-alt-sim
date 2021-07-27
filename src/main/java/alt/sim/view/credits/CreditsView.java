package alt.sim.view.credits;

import alt.sim.view.common.CommonView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Not the best, awt should be replaced.
 */
public class CreditsView {

    @FXML
    private Hyperlink readMeLink;

    private static final String URL = "https://github.com/andreafoschi00/OOP20-alt-sim/blob/master/README.md";

    @FXML
    public void initialize() {
        readMeLink.setOnAction(this::loadURL);
    }

    private void loadURL(final ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(URL));
        } catch (final IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoBackClick(final ActionEvent event) {
        CommonView.goBack();
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
