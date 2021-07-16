package alt.sim.view;

import java.io.IOException;

import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class CommonView {

    private static final PageLoader PAGE_LOADER = new PageLoader();
    private static final Stage POPUP_STAGE = new Stage(StageStyle.UNDECORATED);

    private CommonView() {
    }

    public static void goBack() {
        PAGE_LOADER.loadPage(Page.HOME);
    }

    public static void onPauseClick() throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/PauseDialog.fxml"));
        POPUP_STAGE.setScene(new Scene(root));
        POPUP_STAGE.showAndWait();
    }
}
