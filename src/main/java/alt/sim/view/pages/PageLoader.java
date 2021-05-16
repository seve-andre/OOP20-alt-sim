package alt.sim.view.pages;

import java.io.IOException;

import alt.sim.view.mapchoice.GameMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageLoader {

    /**
     * Loads page given as argument.
     *
     * @param stage to whom load new content
     * @param page to load
     * @param gameMap to load
     */
    public void loadPage(final Stage stage, final Page page, final GameMap gameMap) {

        /**
         * If given page is a GameMap, gets its name.
         */
        if (page.getName().equals(Page.GAME.getName())) {
            page.setName(gameMap.getName());
        }
        Parent root = null;

        try {
            root = FXMLLoader.load(ClassLoader.
                    getSystemResource("layouts/" + page.getName() + ".fxml"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
    }


    /**
     * Method overloading.
     *
     * @param stage to whom load new content
     * @param page to load
     */
    public void loadPage(final Stage stage, final Page page) {
        this.loadPage(stage, page, null);
    }
}
