package org.util.stats;

import org.util.dto.ErrorInfo;
import org.util.dto.Report;

public class StatisticsCollector {
    private final Statistics statistics = new Statistics();

    public void acceptSuccess() {
        statistics.updateValid();
    }

    public void acceptError(ErrorInfo errorInfo) {
        statistics.updateError(errorInfo);
    }

    public Report getStatsDto() {
        return statistics.statsToDto();
    }
}
