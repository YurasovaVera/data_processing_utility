import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.util.dto.ErrorInfo;
import org.util.dto.ParsedEntry;
import org.util.parser.FileParser;
import org.util.parser.LineParser;
import org.util.reader.SourceReader;

class FileParsingTest {
    private FileParser fileParser;
    private static final String VALID_LINE_1 = "1;John Doe;70.5";
    private static final String VALID_LINE_2 = "2;Jane Smith;65.0";

    @BeforeEach
    void setUp() {
        fileParser = new FileParser(new LineParser(), new SourceReader());
    }

    @Test
    void localFileProcessingTest(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, List.of(VALID_LINE_1, VALID_LINE_2));

        List<ParsedEntry> successfulEntries = new ArrayList<>();
        List<ErrorInfo> errors = new ArrayList<>();

        fileParser.parse(testFile.toString(), (entry, lineNumber) -> successfulEntries.add(entry), errors::add);

        assertEquals(2, successfulEntries.size());
        assertEquals(0, errors.size());
        assertEquals(1L, successfulEntries.getFirst().id());
        assertEquals("John Doe", successfulEntries.getFirst().name());
        assertEquals(70.5, successfulEntries.getFirst().weight());
    }

    @Test
    void emptyFileTest(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("empty.txt");
        Files.createFile(testFile); // пустой файл

        List<ParsedEntry> successfulEntries = new ArrayList<>();
        List<ErrorInfo> errors = new ArrayList<>();

        fileParser.parse(testFile.toString(), (entry, lineNumber) -> successfulEntries.add(entry), errors::add);

        assertEquals(0, successfulEntries.size());
        assertEquals(0, errors.size());
    }

    @Test
    void damagedFileTest(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("damaged.txt");
        Files.write(testFile, List.of(VALID_LINE_1, "invalid line", "2,Jane,abc", VALID_LINE_2, " , , "));

        List<ParsedEntry> successfulEntries = new ArrayList<>();
        List<ErrorInfo> errors = new ArrayList<>();

        fileParser.parse(testFile.toString(), (entry, lineNumber) -> successfulEntries.add(entry), errors::add);

        assertEquals(2, successfulEntries.size());
        assertEquals(3, errors.size());
        assertEquals(2, errors.get(0).lineNumber());
        assertEquals(3, errors.get(1).lineNumber());
        assertEquals(5, errors.get(2).lineNumber());
    }
}
