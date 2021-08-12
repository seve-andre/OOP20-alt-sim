package alt.sim.view.loading;

import alt.sim.controller.loading.LoadingController;
import alt.sim.view.pages.Page;
import alt.sim.view.pages.PageLoader;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoadingView {

    @FXML
    private ProgressBar loadingBar = new ProgressBar();
    private static final int LOADING_TIME = 100;

    @FXML
    public void initialize() {
        new LoadingController().loading();
        loadingBar.progressProperty().bind(task.progressProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        // After loading is done, goes to HOME Page.
        task.setOnSucceeded(e -> PageLoader.loadPage(Page.HOME));
    }

    /**
     * Updates loading bar every 0.1 s.
     */
    private final Task<Void> task = new Task<>() {
        @Override
        public Void call() {
            for (long i = 0; i < 10; i++) {
                try {
                    Thread.sleep(LOADING_TIME);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                updateProgress(i + 1, 10);
            }
            return null;
        }
    };
}
