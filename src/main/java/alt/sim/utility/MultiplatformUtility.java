package alt.sim.utility;

import java.io.File;

/**
 *
 * Utility class to ensure multi-platform software.
 *
 */
public final class MultiplatformUtility {

    /**
     * Get OS file separator.
     */
    public static final String SEPARATOR = File.separator;

    /**
     * Get user home directory.
     */
    public static final String USER_HOME = System.getProperty("user.home");

    private MultiplatformUtility() { }
}
