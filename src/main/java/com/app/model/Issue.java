package com.app.model;

import com.app.model.common.BaseModel;
import com.app.model.enums.IssueState;
import com.app.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "ISSUE")
public class Issue extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("issue_id")
    private Integer issueId;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @JsonProperty("current_status")
    @Enumerated(EnumType.STRING)
    private IssueState issueState;

    private LocalDateTime createdDate;
    private String createdUser;
    @ElementCollection
    private List<ChangeLog> changeLog;
    private String issueDescription;
    @Column(name = "issue_project_id")
    private Integer projectId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Project project;

}
