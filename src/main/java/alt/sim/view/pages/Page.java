package alt.sim.view.pages;

public enum Page {

    /**
     * Loading page.
     */
    LOADING("Loading"),

    /**
     * Home page.
     */
    HOME("Home"),

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
    GAME("Game");

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

