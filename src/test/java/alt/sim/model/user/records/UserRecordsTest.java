package alt.sim.model.user.records;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import alt.sim.model.user.UserImpl;
import alt.sim.model.user.records.RecordsFolder.RecordsPath;

public class UserRecordsTest {

    private final String user1 = "Paolo";
    private final String user2 = "Luca";
    private UserRecordsImpl userRecords;
    private static final Path USERS_DATA_FILE_PATH = Path.of(RecordsPath.USER_RECORDS_DIR_PATH.getPath());

    @Test
    public void testJson() throws Exception {
        this.userRecords.addUser(new UserImpl(this.user1, 100));
        assertEquals(this.userRecords.getUserByName(user1), Optional.of(new UserImpl(user1, 100)));
    }

}
