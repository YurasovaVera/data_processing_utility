package org.util.reporter;

public enum OutputFormat {
    JSON("json"),
    XML("xml");

    OutputFormat(String format) {}

    public static OutputFormat getOutputFormatFromString(String format) {
        return switch (format.toLowerCase()) {
            case "json" -> JSON;
            case "xml" -> XML;
            default -> throw new IllegalArgumentException("Unexpected format");
        };
    }
}
