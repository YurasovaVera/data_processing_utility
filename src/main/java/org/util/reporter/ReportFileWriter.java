package org.util.reporter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportFileWriter {
    public static void writeReportToFile(String report, String fileName, OutputFormat format) {
        Path pathToFile = Path.of(fileName + format.toString());
        try (var writer = Files.newBufferedWriter(pathToFile, StandardCharsets.UTF_8)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Failed to record report", e);
        }
    }
}
