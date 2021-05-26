package alt.sim.view.mapchoice;

import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * Gets a random GameMap from values.
     * @return GameMap randomly between values
     */
    public static GameMap getRandomMap() {
        final GameMap[] values = values();
        int index = ThreadLocalRandom.current().nextInt(values.length);
        return values[index];
    }
}
