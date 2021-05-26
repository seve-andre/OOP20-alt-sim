package alt.sim.view.mapchoice;

import java.util.ArrayList;
import java.util.List;
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
     * Gets maps names into a list.
     * @return list with all maps names
     */
    public static List<String> getNamesList() {
        final List<String> names = new ArrayList<>(4);
        for (GameMap gameMap : GameMap.values()) {
            names.add(gameMap.getName());
        }
        return names;
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
