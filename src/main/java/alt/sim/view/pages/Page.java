package alt.sim.view.pages;

public enum Page {

    /**
     * Home page.
     */
    HOME("Home"),

    /**
     * Map choice page.
     */
    MAP_CHOICE("MapChoice"),

    /**
     * Leaderboard page.
     */
    LEADERBOARD("Leaderboard"),

    /**
     * Credits page.
     */
    CREDITS("Credits"),

    /**
     * Game page.
     */
    GAME("Game"),

    /**
     * GameOver page.
     */
    GAMEOVER("GameOver");

    private String name;

    Page(final String name) {
        this.name = name;
    }

    /**
     *
     * @return layouts fxml file name
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

