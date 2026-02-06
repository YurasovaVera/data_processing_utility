package org.util.reporter;

public class ReportFactory {
    public static Reporter createReporter(OutputFormat format) {
        return switch (format) {
            case JSON -> new JsonReporter();
            case XML -> new XmlReporter();
        };
    }
}
