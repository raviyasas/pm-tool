package com.app.model;

import com.app.model.common.BaseModel;
import com.app.model.enums.IssueState;
import com.app.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ISSUE")
@EqualsAndHashCode
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

    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    private String createdUser;

    @JsonProperty("issue_description")
    private String issueDescription;

    @JsonProperty("project_id")
    @Column(name = "issue_project_id")
    private Integer projectId;
    private Boolean isAssigned;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @ManyToMany(mappedBy = "issues")
    @JsonIgnore
    private List<AppUser> users = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "issue")
    private List<ChangeLog> changeLog;

}
