package alt.sim.model.user.records;

import alt.sim.utility.MultiplatformUtility.Utility;

/**
 * Class for records file purposes.
 */
public final class RecordsFolder {

    private static final String USER_HOME_DIR = Utility.USER_HOME.getProperty();
    private static final String SEPARATOR = Utility.SEPARATOR.getProperty();
    private static final String RECORDS_FOLDER_NAME = ".altsim";
    private static final String RECORDS_DIRECTORY_NAME = "user_records";
    private static final String RECORDS_FILE_NAME = "users.json";

    public enum RecordsPath {

        /**
         * Path to records directory.
         */
        RECORDS_DIR_PATH(USER_HOME_DIR + SEPARATOR + RECORDS_FOLDER_NAME),

        /**
         * Path to user_records directory.
         */
        USER_RECORDS_DIR_PATH(RECORDS_DIR_PATH.getPath() + SEPARATOR + RECORDS_DIRECTORY_NAME),

        /**
         * Path to json file in user_records directory.
         */
        USER_RECORDS_FILE_PATH(USER_RECORDS_DIR_PATH.getPath() + SEPARATOR + RECORDS_FILE_NAME);

        private final String path;

        RecordsPath(final String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }
}
