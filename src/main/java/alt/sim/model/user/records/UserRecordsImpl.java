package alt.sim.model.user.records;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import alt.sim.model.user.User;
import alt.sim.model.user.records.RecordsFolder.RecordsPath;
import alt.sim.model.user.validation.RecordsValidation;

public class UserRecordsImpl implements UserRecords {

    private final Path jsonPath = Path.of(RecordsPath.USER_RECORDS_FILE_PATH.getPath());
    private final RecordsValidation recordsValidation = new RecordsValidation();

    private LinkedHashMap<String, Integer> users = new LinkedHashMap<>();

    private final Type jsonTypeToken = new TypeToken<LinkedHashMap<String, Integer>>() { }.getType();

    /**
     * Loads users from file.
     * @throws IOException
     */
    public void loadFile() throws IOException {
        this.recordsValidation.userRecordsFileValidation();
        final String jsonString = Files.readString(this.jsonPath);
        this.users = new Gson().fromJson(jsonString, this.jsonTypeToken);
        if (this.users == null) {
            this.users = new LinkedHashMap<>();
        }
    }

    /**
     * Appends to file.
     *
     * @throws IOException
     */
    public void updateFile() throws IOException {
        final String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this.users, this.jsonTypeToken);
        this.recordsValidation.userRecordsFileValidation();
        Files.writeString(this.jsonPath, json);
    }

    /**
     * {@inheritDoc}
     * @throws IOException
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
     * @throws IOException
     */
    @Override
    public boolean isPresent(final String name) throws IOException {
        this.loadFile();
        return this.users.containsKey(name);
    }

    public LinkedHashMap<String, Integer> getUsers() {
        try {
            this.loadFile();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return this.users;
    }

    /**
     * {@inheritDoc}
     * @throws IOException
     */
    @Override
    public void updateScore(final String name) throws IOException {
        this.loadFile();
        if (this.users.containsKey(name)) {
            this.users.remove(name);
            this.users.put(name, 100);
        }
        this.updateFile();
    }

    /**
     * Gets last name in the map a.k.a. last key.
     * @return last key map
     */
    public String getLastKey() {
        return (String) this.getUsers().keySet().toArray()[users.size() - 1];
    }
}
