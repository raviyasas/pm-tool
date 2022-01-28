package com.app.model;

import com.app.model.common.BaseModel;
import com.app.model.enums.IssueState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class ChangeLog extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime changedTime;

    @JsonProperty("from_state")
    @Enumerated(EnumType.STRING)
    private IssueState previousState;

    @JsonProperty("to_state")
    @Enumerated(EnumType.STRING)
    private IssueState currentState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Issue issue;
}
