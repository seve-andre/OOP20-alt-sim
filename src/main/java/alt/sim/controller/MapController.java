package alt.sim.controller;

import alt.sim.model.user.records.UserRecordsImpl;

public final class MapController {

    private static final UserRecordsImpl USER_RECORDS = new UserRecordsImpl();

    private MapController() { }

    /**
     * Gets name of last key.
     * @return name
     */
    public static String getName() {
        return USER_RECORDS.getLastKey();
    }
}
