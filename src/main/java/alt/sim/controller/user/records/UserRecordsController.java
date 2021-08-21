package alt.sim.controller.user.records;

import alt.sim.model.user.records.UserRecordsImpl;

import java.io.IOException;

public final class UserRecordsController {

    private UserRecordsController() { }

    public static void updateScore(final String name, final int score) throws IOException {
        new UserRecordsImpl().updateScore(name, score);
    }
}
