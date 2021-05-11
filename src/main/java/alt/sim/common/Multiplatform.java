package alt.sim.common;

import java.io.File;

/**
 *
 * Class to ensure multi-platform software.
 *
 */
public final class Multiplatform {

    public enum OSDependent {

        /**
         * Gets OS file separator.
         */
        SEPARATOR(File.separator),

        /**
         * Gets user home directory.
         */
        USER_HOME(System.getProperty("user.home"));

        private final String property;

        OSDependent(final String property) {
            this.property = property;
        }

        public String getProperty() {
            return this.property;

        }
    }
}
