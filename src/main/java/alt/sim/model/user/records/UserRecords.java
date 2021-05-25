package alt.sim.model.user.records;

import java.io.IOException;

import alt.sim.model.user.User;

public interface UserRecords {

    /**
     *
     * @param user to add to records
     */
    void addUser(User user) throws IOException;

    /**
     *
     * @param name to check existence
     * @return true if present in records
     */
    boolean isPresent(String name) throws IOException;
}
