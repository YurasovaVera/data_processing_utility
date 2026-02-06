package org.util.validator;

import java.nio.file.Files;
import java.nio.file.Path;
import org.util.reporter.OutputFormat;

public class ArgumentsValidator {
    public static void validate(String path, String format) {
        validateFormat(format);
        validatePathExtension(path);
        validateFileExists(path);
    }

    private static void validateFormat(String format) {
        if (OutputFormat.getOutputFormatFromString(format) == null) {
            throw new IllegalArgumentException("format are requested in an unsupported format");
        }
    }

    private static void validateFileExists(String file) {
        Path path = Path.of(file);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("file does not exist");
        }
    }

    private static void validatePathExtension(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1);
        if (!extension.equals("txt")) {
            throw new IllegalArgumentException("path extension is not supported");
        }
    }
}
