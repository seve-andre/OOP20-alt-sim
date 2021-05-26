package alt.sim.view.loading;

import alt.sim.Main;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoadingView {

    @FXML
    private ProgressBar loadingBar = new ProgressBar();
    private static final int LOADING_TIME = 2000;
    private PageLoader pageLoader = new PageLoader();

    @FXML
    public void initialize() {
        loadingBar.progressProperty().bind(task.progressProperty());
        loadingBar.progressProperty().addListener(observable -> {
            pageLoader.loadPage(Main.getStage(), Page.HOME);
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private Task<Void> task = new Task<Void>() {
        @Override
        public Void call() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(LOADING_TIME);
                } catch (final InterruptedException e) {
                      Thread.interrupted();
                      break;
                }
                updateProgress(i + 1, 10);
            }
            return null;
        }
    };
}
