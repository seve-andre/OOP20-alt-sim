package alt.sim.view.common;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public final class CommonView {


    private CommonView() {
    }

    public static void goBack() {
        PageLoader.loadPage(Page.HOME);
    }

    public static void pause() throws IOException {
        Stage POPUP_STAGE = new Stage(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/PauseDialog.fxml"));
        POPUP_STAGE.setScene(new Scene(root));
        POPUP_STAGE.showAndWait();
    }
    
    public static void minimize() {
        Main.getStage().setIconified(true);
    }

    public static void close() {
        Platform.exit();
        System.exit(0);
    }
}
