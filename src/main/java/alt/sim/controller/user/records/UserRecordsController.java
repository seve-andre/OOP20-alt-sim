package alt.sim.controller.user.records;

import alt.sim.model.user.records.UserRecordsImpl;

import java.io.IOException;

public final class UserRecordsController {

    private UserRecordsController() { }

    /**
     * Updates score of user.
     * @param name of the user
     * @param score achieved
     * @throws IOException if user does not exist in file
     */
    public static void updateScore(final String name, final int score) throws IOException {
        new UserRecordsImpl().updateScore(name, score);
    }
}
