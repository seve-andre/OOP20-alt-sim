package alt.sim.view;

import java.io.IOException;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class CommonView {

    private static final PageLoader PAGE_LOADER = new PageLoader();
    private static final Stage POPUP_STAGE = new Stage(StageStyle.UNDECORATED);

    private CommonView() { }

    @FXML
    public static void goBack() {
        PAGE_LOADER.loadPage(Main.getStage(), Page.HOME);
    }

    @FXML
    public static void onPauseClick() throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/PauseDialog.fxml"));
        POPUP_STAGE.setScene(new Scene(root));
        POPUP_STAGE.initModality(Modality.APPLICATION_MODAL);
        POPUP_STAGE.showAndWait();
    }
}
