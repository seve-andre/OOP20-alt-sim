package alt.sim.view.common;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public final class CommonView {


    private CommonView() { }

    /**
     * Goes back to homepage.
     */
    public static void goBack() {
        PageLoader.loadPage(Page.HOME);
    }

    /**
     * Creates pop up dialog.
     * @throws IOException if fxml loader fails
     */
    public static void showDialog(final Page page) throws IOException {
        Stage popupStage = new Stage(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/" + page.getName() + ".fxml"));
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    /**
     * Minimizes window.
     */
    public static void minimize() {
        Main.getStage().setIconified(true);
    }

    /**
     * Closes window.
     */
    public static void close() {
        Platform.exit();
        System.exit(0);
    }
}
