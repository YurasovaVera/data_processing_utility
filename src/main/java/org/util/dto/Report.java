package org.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Report(
        @JsonProperty("totalRecordCount") long totalRecordCount,
        @JsonProperty("validRecordCount") long validRecordCount,
        @JsonProperty("invalidRecordCount") long invalidRecordCount,
        @JsonProperty("errorsList") List<ErrorInfo> errorsInfoList) {}
