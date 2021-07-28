package alt.sim.model.spawn;

import java.util.concurrent.ThreadLocalRandom;

public enum SpawnLocation {

    /**
     * Left spawn location.
     */
    LEFT,

    /**
     * Top spawn location.
     */
    TOP,

    /**
     * Right spawn location.
     */
    RIGHT,

    /**
     * Bottom spawn location.
     */
    BOTTOM;

    /**
     * Gets a random SpawnLocation from values.
     * @return SpawnLocation randomly between values
     */
    public static SpawnLocation getRandomSpawnLocation() {
        final SpawnLocation[] values = values();
        int index = ThreadLocalRandom.current().nextInt(values.length);
        return values[index];
    }
}
