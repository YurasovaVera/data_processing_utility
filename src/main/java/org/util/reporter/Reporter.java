package org.util.reporter;

import java.io.IOException;
import org.util.dto.Report;

public interface Reporter {
    String createReport(Report report) throws IOException;
}
