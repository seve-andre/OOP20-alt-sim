package alt.sim.view;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.fxml.FXML;

public final class CommonView {

    private static final PageLoader PAGE_LOADER = new PageLoader();

    private CommonView() { }

    @FXML
    public static void goBack() {
        PAGE_LOADER.loadPage(Main.getStage(), Page.HOME);
    }
}
