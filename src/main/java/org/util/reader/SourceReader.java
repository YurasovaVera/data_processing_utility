package org.util.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourceReader {
    public BufferedReader createReader(String path) throws IOException {
        return Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
    }
}
