package com.app.model.request;

import com.app.model.ChangeLog;
import com.app.model.enums.IssueState;
import com.app.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssueRequest {

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @JsonProperty("current_status")
    @Enumerated(EnumType.STRING)
    private IssueState issueState;

    private String createdUser;
    private List<ChangeLog> changeLog;
    private String issueDescription;
    private Integer projectId;
}
