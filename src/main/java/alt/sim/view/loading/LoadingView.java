package alt.sim.view.loading;

import alt.sim.view.pages.PageLoader;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LoadingView {

    @FXML
    private ProgressBar loadingBar = new ProgressBar();
    private volatile boolean done = false;
    private PageLoader pageLoader = new PageLoader();

    @FXML
    public void initialize() {
        loadingBar.progressProperty().bind(task.progressProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        /*Platform.runLater(() -> {
            pageLoader.loadPage(Main.getStage(), Page.HOME);
        });*/
    }

    private Task<Void> task = new Task<Void>() {
        @Override
        public Void call() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (final InterruptedException e) {
                      Thread.interrupted();
                      break;
                }
                System.out.println(i + 1);
                updateProgress(i + 1, 10);
            }
            return null;
        }
    };
}
