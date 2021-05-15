package alt.sim.view.mapchoice;

public enum GameMap {

    /**
     * Seaside map.
     */
    SEASIDE("Seaside"),

    /**
     * Countryside map.
     */
    COUNTRYSIDE("Countryside"),

    /**
     * Riverside map.
     */
    RIVERSIDE("Riverside"),

    /**
     * Cityside map.
     */
    CITYSIDE("Cityside");

    private String name;

    GameMap(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
