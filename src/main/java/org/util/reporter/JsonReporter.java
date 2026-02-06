package org.util.reporter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.util.dto.Report;

public class JsonReporter implements Reporter {
    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String createReport(Report report) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
        } catch (IOException e) {
            throw new RuntimeException("Failed to record json report", e);
        }
    }
}
