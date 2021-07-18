package alt.sim.controller.user.records;

import java.io.IOException;

import alt.sim.model.user.records.UserRecordsImpl;

public final class UserRecordsController {

    private UserRecordsController() { }

    private static final UserRecordsImpl USER_RECORDS = new UserRecordsImpl();

    public static void updateScore(final String name, final int score) throws IOException {
        USER_RECORDS.updateScore(name, score);
    }
}
