import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.util.validator.ArgumentsValidator;

public class ArgumentsValidatorTest {
    @Test
    void validateWithUnsupportedFormatShouldThrowException() {
        String invalidFormat = "unsupported";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> ArgumentsValidator.validate("test.txt", invalidFormat));

        assertEquals("Unexpected format", exception.getMessage());
    }

    @Test
    void validateWithUnsupportedExtensionShouldThrowException() {
        String invalidPath = "test.pdf";

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> ArgumentsValidator.validate(invalidPath, "json"));

        assertEquals("Path extension is not supported", exception.getMessage());
    }

    @Test
    void validateWithExistingFileShouldPassValidation(@TempDir Path tempDir) throws IOException {
        Path existingFile = tempDir.resolve("test.txt");
        Files.createFile(existingFile);

        assertDoesNotThrow(() -> ArgumentsValidator.validate(existingFile.toString(), "json"));
    }

    @Test
    void validateWithNonExistentFileShouldThrowFileDoesNotExistException() {
        String nonExistentFile = "C:/nonexistent/file.txt";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> ArgumentsValidator.validate(nonExistentFile, "json"));

        assertEquals("File does not exist", exception.getMessage());
    }

    @Test
    void validateWithExistingDirectoryInsteadOfFileShouldThrowException(@TempDir Path tempDir) throws IOException {
        Path directory = tempDir.resolve("folder.txt");
        Files.createDirectory(directory);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> ArgumentsValidator.validate(directory.toString(), "json"));

        assertTrue(exception.getMessage().contains("Path is not a file"));
    }
}
