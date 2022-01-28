package com.app.model;

import com.app.model.common.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "APP_USER")
public class AppUser extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("email")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_issue",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Issue> issues = new ArrayList<>();

}
