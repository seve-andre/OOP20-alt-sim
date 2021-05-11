package alt.sim.utility;

import java.io.File;

/**
 *
 * Utility class to ensure multi-platform software.
 *
 */
public final class MultiplatformUtility {

    public enum Utility {

        /**
         * Gets OS file separator.
         */
        SEPARATOR(File.separator),

        /**
         * Gets user home directory.
         */
        USER_HOME(System.getProperty("user.home"));

        private final String property;

        Utility(final String property) {
            this.property = property;
        }

        public String getProperty() {
            return this.property;

        }
    }
}
