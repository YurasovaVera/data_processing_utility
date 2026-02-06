package org.util.stats;

import java.util.ArrayList;
import java.util.List;
import org.util.dto.ErrorInfo;
import org.util.dto.Report;

public class Statistics {
    private long totalRecordCount;
    private long validRecordCount;
    private long invalidRecordCount;
    private final List<ErrorInfo> errors = new ArrayList<>();

    public void updateValid() {
        totalRecordCount++;
        validRecordCount++;
    }

    public void updateError(ErrorInfo errorInfo) {
        totalRecordCount++;
        invalidRecordCount++;
        errors.add(errorInfo);
    }

    public Report statsToDto() {
        return new Report(totalRecordCount, validRecordCount, invalidRecordCount, new ArrayList<>(errors));
    }
}
