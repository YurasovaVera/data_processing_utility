package org.util;

import java.util.List;
import org.util.dto.ErrorInfo;
import org.util.dto.Report;
import org.util.parser.FileParser;
import org.util.parser.LineParser;
import org.util.reader.SourceReader;
import org.util.reporter.OutputFormat;
import org.util.reporter.ReportFactory;
import org.util.reporter.ReportFileWriter;
import org.util.reporter.Reporter;
import org.util.stats.StatisticsCollector;
import org.util.validator.ArgumentsValidator;
import org.util.validator.EntityValidator;
import picocli.CommandLine;

public class Main implements Runnable {
    @CommandLine.Option(names = "--path", required = true, description = "path to file")
    private String path;

    @CommandLine.Option(names = "--format", description = "output format of the report", defaultValue = "JSON")
    private String format;

    private static final String OUTPUT_FILE_NAME = "report.";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            ArgumentsValidator.validate(path, format);
            StatisticsCollector statisticsCollector = new StatisticsCollector();
            FileParser fileParser = new FileParser(new LineParser(), new SourceReader());
            EntityValidator validator = new EntityValidator();

            fileParser.parse(
                    path,
                    (entry, lineNumber) -> {
                        List<ErrorInfo> errors = validator.validateParsedEntry(entry, lineNumber);
                        if (errors.isEmpty()) {
                            statisticsCollector.acceptSuccess();
                        } else {
                            errors.forEach(statisticsCollector::acceptError);
                        }
                    },
                    statisticsCollector::acceptError);
            Reporter reporter = ReportFactory.createReporter(OutputFormat.getOutputFormatFromString(format));
            Report report = statisticsCollector.getStatsDto();
            ReportFileWriter.writeReportToFile(
                    reporter.createReport(report), OUTPUT_FILE_NAME, OutputFormat.getOutputFormatFromString(format));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
