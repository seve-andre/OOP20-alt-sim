package alt.sim.model.user.records;

import alt.sim.model.user.User;
import alt.sim.model.user.records.RecordsFolder.RecordsPath;
import alt.sim.model.user.records.adapter.FileOperationsAdapter;
import alt.sim.model.user.validation.RecordsValidation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.LinkedHashMap;

public class UserRecordsImpl extends FileOperationsAdapter implements UserRecords {

    private final Path jsonPath = Path.of(RecordsPath.USER_RECORDS_FILE_PATH.getPath());
    private final RecordsValidation recordsValidation = new RecordsValidation();

    private Map<String, Integer> users = new LinkedHashMap<>();

    // token needed by Gson to store java.util.Map to file
    private final Type jsonTypeToken = new TypeToken<LinkedHashMap<String, Integer>>() { }.getType();

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFile(final Path path) throws IOException {
        this.recordsValidation.userRecordsFileValidation();
        final String jsonString = Files.readString(path);
        this.users = new Gson().fromJson(jsonString, this.jsonTypeToken);
        if (this.users == null) {
            this.users = new LinkedHashMap<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFile(final Path path) throws IOException {
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
        this.loadFile(this.jsonPath);
        if (!this.users.containsKey(user.getName().trim())) {
            this.users.put(user.getName().trim(), user.getScore());
        }
        this.updateFile(this.jsonPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPresent(final String name) throws IOException {
        this.loadFile(this.jsonPath);
        return this.users.containsKey(name);
    }

    public Map<String, Integer> getUsers() {
        try {
            this.loadFile(this.jsonPath);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return this.users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateScore(final String name, final int score) throws IOException {
        this.loadFile(this.jsonPath);
        if (this.users.containsKey(name)) {
            this.users.replace(name, score);
        }
        this.updateFile(this.jsonPath);
    }

    /**
     * Gets last name in the map a.k.a. last key.
     * @return last key map
     */
    public String getLastKey() {
        return (String) this.getUsers().keySet().toArray()[users.size() - 1];
    }
}
