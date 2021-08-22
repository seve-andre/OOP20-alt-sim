package alt.sim.model.user.records.adapter;

import java.io.IOException;
import java.nio.file.Path;

public interface FileOperations {

    /**
     * Creates file by path.
     * @param filePath of the file
     */
    void createFile(Path filePath) throws IOException;

    /**
     * Creates dir by path.
     * @param dirPath of the dir
     */
    void createDirectory(Path dirPath) throws IOException;

    /**
     * Loads file from system.
     * @param filePath of the file
     * @throws IOException if file does not exist.
     */
    void loadFile(Path filePath) throws IOException;

    /**
     * Writes to file.
     * @param filePath of the file
     * @throws IOException if file does not exist
     */
    void updateFile(Path filePath) throws IOException;
}
