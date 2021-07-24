package alt.sim.model.spawn;

import java.util.concurrent.ThreadLocalRandom;

public enum SpawnLocation {
    LEFT,
    TOP,
    RIGHT,
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
