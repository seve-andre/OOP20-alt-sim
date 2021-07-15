package alt.sim.view.pages;

import java.io.IOException;

import alt.sim.Main;
import alt.sim.view.mapchoice.GameMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PageLoader {

    /**
     * Loads page given as argument.
     *  @param page to load
     * @param gameMap to load
     */
    public void loadPage(final Page page, final GameMap gameMap) {

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

        Main.getStage().setScene(new Scene(root));
    }

    /**
     * Method overloading.
     *
     * @param page to load
     */
    public void loadPage(final Page page) {
        this.loadPage(page, null);
    }
}
