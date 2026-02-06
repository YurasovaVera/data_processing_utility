package org.util.reporter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import org.util.dto.Report;

public class XmlReporter implements Reporter {
    private final XmlMapper mapper = new XmlMapper();

    @Override
    public String createReport(Report report) throws IOException {
        try {
            return mapper.writeValueAsString(report);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
