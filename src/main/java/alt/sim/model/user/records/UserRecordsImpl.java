package alt.sim.model.user.records;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import alt.sim.model.user.User;
import alt.sim.model.user.records.RecordsFolder.RecordsPath;
import alt.sim.model.user.validation.RecordsValidation;

public class UserRecordsImpl implements UserRecords {

    private final Path jsonPath = Path.of(RecordsPath.USER_RECORDS_FILE_PATH.getPath());
    private final RecordsValidation recordsValidation = new RecordsValidation();

    private Map<String, Integer> users = new HashMap<>();

    private final Type jsonTypeToken = new TypeToken<Map<String, Integer>>() { }.getType();

    /**
     * Loads users from file.
     * @throws IOException
     */
    public void loadFile() throws IOException {
        this.recordsValidation.userRecordsFileValidation();
        final String jsonString = Files.readString(this.jsonPath);
        this.users = new Gson().fromJson(jsonString, this.jsonTypeToken);
    }

    /**
     * Appends to file.
     *
     * @throws IOException
     */
    public void updateFile() throws IOException {
        this.loadFile();
        final String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this.users, this.jsonTypeToken);
        this.recordsValidation.userRecordsFileValidation();
        Files.writeString(this.jsonPath, json);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(final User user) throws IOException {
        this.loadFile();
        if (!this.users.containsKey(user.getName())) {
            this.users.put(user.getName(), user.getScore());
        }
        this.updateFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPresent(final String name) throws IOException {
        this.loadFile();
        return this.users.containsKey(name);
    }

    public Map<String, Integer> getUsers() {
        try {
            this.loadFile();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return this.users;
    }
}
