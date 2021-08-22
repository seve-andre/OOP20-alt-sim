package alt.sim.model.user.records.adapter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOperationsAdapter implements FileOperations {

    @Override
    public void createFile(final Path filePath) throws IOException {
        if (!Files.isRegularFile(filePath)) {
            Files.deleteIfExists(filePath);
        }
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }
    }

    @Override
    public void createDirectory(final Path dirPath) throws IOException {
        if (!Files.isDirectory(dirPath)) {
            Files.deleteIfExists(dirPath);
        }
        if (Files.notExists(dirPath)) {
            Files.createDirectory(dirPath);
        }
    }

    @Override
    public void loadFile(final Path filePath) throws IOException {
        Files.readAllLines(filePath);
    }

    @Override
    public void updateFile(final Path filePath) throws IOException {
        List<String> lines = List.of();
        Files.write(filePath, lines, Charset.defaultCharset());
    }
}
