package com.app.model.request;

import com.app.model.ChangeLog;
import com.app.model.enums.IssueState;
import com.app.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class IssueRequest {

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @JsonProperty("current_state")
    @Enumerated(EnumType.STRING)
    private IssueState issueState;

    @JsonProperty("created_user")
    private String createdUser;

    @JsonProperty("change_log")
    private ChangeLog changeLog;

    @JsonProperty("issue_description")
    private String issueDescription;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("issue_id")
    private Integer issueId;

    @JsonProperty("user_id")
    private Integer userId;
}
