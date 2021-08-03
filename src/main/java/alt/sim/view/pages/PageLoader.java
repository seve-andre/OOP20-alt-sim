package alt.sim.view.pages;

import alt.sim.Main;
import alt.sim.view.mapchoice.GameMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public final class PageLoader {

    private static final double HEIGHT_GAME = 720;
    private static final double WIDTH_GAME = 1280;

    private static final double HEIGHT_HOME = 540;
    private static final double WIDTH_HOME = 960;

    private PageLoader() { }

    /**
     * Loads page given as argument.
     *  @param page to load
     * @param gameMap to load
     */
    public static void loadPage(final Page page, final GameMap gameMap) {

        if (page.getName().equals(Page.GAME.getName())) {
            page.setName(gameMap.getName());
            Main.getStage().setHeight(HEIGHT_GAME);
            Main.getStage().setWidth(WIDTH_GAME);
        } else {
            Main.getStage().setHeight(HEIGHT_HOME);
            Main.getStage().setWidth(WIDTH_HOME);
        }

        Parent root = null;

        try {
            root = FXMLLoader.load(ClassLoader.
                    getSystemResource("layouts/" + page.getName() + ".fxml"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        if (root != null) {
            Main.getStage().setScene(new Scene(root));
            Main.getStage().centerOnScreen();
        }
    }

    /**
     * Method overloading.
     *
     * @param page to load
     */
    public static void loadPage(final Page page) {
        loadPage(page, null);
    }
}
