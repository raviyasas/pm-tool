package com.app.model;

import com.app.model.common.BaseModel;
import com.app.model.enums.IssueState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class ChangeLog extends BaseModel {

    private LocalDateTime changedTime;

    @JsonProperty("to_state")
    @Enumerated(EnumType.STRING)
    private IssueState currentState;

    @JsonProperty("from_state")
    @Enumerated(EnumType.STRING)
    private IssueState previousState;
}
