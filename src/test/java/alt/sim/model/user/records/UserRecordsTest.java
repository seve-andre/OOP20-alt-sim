package alt.sim.model.user.records;

import alt.sim.model.user.records.RecordsFolder.RecordsPath;
import alt.sim.model.user.validation.RecordsValidation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRecordsTest {

    private final String user1 = "Paolo";
    private final String user2 = "Luca";
    private final String user3 = "Giacomo";

    private final List<String> usersNames = List.of(user1, user2, user3);

    private Map<String, Integer> users = new UserRecordsImpl().getUsers();
    private static final Path USER_RECORDS_FILE_PATH = Path.of(RecordsPath.USER_RECORDS_FILE_PATH.getPath());
    private final Type jsonTypeToken = new TypeToken<LinkedHashMap<String, Integer>>() { }.getType();

    // static modifier REQUIRED by BeforeAll tag;
    // to remove static, should annotate class with tag
    // TestInstance(Lifecycle.PER_CLASS)
    @BeforeAll
    public static void initialize() throws IOException {
        if (Files.exists(USER_RECORDS_FILE_PATH)) {
            Files.copy(USER_RECORDS_FILE_PATH,
                    Path.of(USER_RECORDS_FILE_PATH + ".copy"));
            System.out.println("File exists. Creating a copy!");
        } else {
            System.out.println("File does not exist. I'm creating it now for you!");
            new RecordsValidation().userRecordsFileValidation();
        }
    }

    @BeforeEach
    public void add() throws IOException {
        this.users.put(user1, 0);
        this.users.put(user2, 0);
        final String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this.users, this.jsonTypeToken);
        Files.writeString(Path.of(USER_RECORDS_FILE_PATH + ".copy"), json);
    }

    @Test
    void isNameTaken() throws IOException {

        for (String usersName : usersNames) {
            if (isInFile(usersName)) {
                System.out.println(usersName + " is in the list");
                assertTrue(this.users.containsKey(usersName));
            } else {
                assertFalse(this.users.containsKey(usersName));
                System.out.println(usersName + " is not in the list");
            }
        }
    }

    boolean isInFile(final String name) throws IOException {
        final String jsonString = Files.readString(Path.of(USER_RECORDS_FILE_PATH + ".copy"));
        this.users = new Gson().fromJson(jsonString, this.jsonTypeToken);
        return this.users.containsKey(name);
    }

    @AfterAll
    static void deleteFileCopy() {
        try {
            Files.deleteIfExists(Path.of(USER_RECORDS_FILE_PATH + ".copy"));
            System.out.println("Deleting file copy...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
