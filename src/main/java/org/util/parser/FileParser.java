package org.util.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.util.dto.ErrorInfo;
import org.util.dto.ParsedEntry;
import org.util.reader.SourceReader;

public class FileParser {
    private final LineParser lineParser;
    private final SourceReader sourceReader;

    public FileParser(LineParser lineParser, SourceReader sourceReader) {
        this.lineParser = lineParser;
        this.sourceReader = sourceReader;
    }

    public void parse(String path, BiConsumer<ParsedEntry, Integer> successConsumer, Consumer<ErrorInfo> errorConsumer)
            throws IOException {
        try (BufferedReader reader = sourceReader.createReader(path)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                handleLine(line, lineNumber, successConsumer, errorConsumer);
            }
        }
    }

    private void handleLine(
            String line,
            int lineNumber,
            BiConsumer<ParsedEntry, Integer> successConsumer,
            Consumer<ErrorInfo> errorConsumer) {
        try {
            ParsedEntry entry = lineParser.parseLine(line);
            successConsumer.accept(entry, lineNumber);
        } catch (Exception e) {
            errorConsumer.accept(new ErrorInfo(lineNumber, e.getMessage()));
        }
    }
}
