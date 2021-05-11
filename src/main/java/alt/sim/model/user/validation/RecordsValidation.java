package alt.sim.model.user.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import alt.sim.model.user.records.RecordsFolder.RecordsPath;

public class RecordsValidation {

    private static final Path USER_RECORDS_FILE_PATH = Path.of(RecordsPath.USER_RECORDS_FILE_PATH.getPath());
    private static final Path USER_RECORDS_DIR_PATH = Path.of(RecordsPath.USER_RECORDS_DIR_PATH.getPath());
    private static final Path RECORDS_DIR_PATH = Path.of(RecordsPath.RECORDS_DIR_PATH.getPath());

    /**
     * Validates directory
     * that contains json file.
     *
     * @throws IOException
     */
    public void userRecordsDirValidation() throws IOException {
        this.dirValidation(USER_RECORDS_DIR_PATH);
    }

    /**
     * Checks if given path is directory.
     * If not, deletes it.
     *
     * @param path
     * @throws IOException
     */
    private void dirValidation(final Path path) throws IOException {
        this.checkDirExistence();
        if (!Files.isDirectory(path)) {
            Files.deleteIfExists(path);
        }
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * Checks "hidden" folder existence by path.
     *
     * @throws IOException
     */
    public void checkDirExistence() throws IOException {
        if (!Files.isDirectory(RECORDS_DIR_PATH)) {
            Files.deleteIfExists(RECORDS_DIR_PATH);
        }
        if (Files.notExists(RECORDS_DIR_PATH)) {
            Files.createDirectory(RECORDS_DIR_PATH);
        }
    }

    /**
     * Validates json file path.
     *
     * @throws IOException
     */
    public void userRecordsFileValidation() throws IOException {
        this.fileValidation(USER_RECORDS_FILE_PATH);
    }

    /**
     * Checks if given path is a file.
     * If not, deletes it.
     *
     * @param path
     * @throws IOException
     */
    private void fileValidation(final Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            Files.deleteIfExists(path);
        }
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
    }
}
