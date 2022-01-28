package com.app.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum IssueType {

    BUG("bug"),
    STORY("story"),
    TASK("task");

    private String issueType;

    private IssueType(String issueType) {
        this.issueType = issueType;
    }

    @JsonCreator
    public static IssueType decode(final String code) {
        return Stream.of(IssueType.values()).filter(targetEnum -> targetEnum.issueType.equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getCode() {
        return issueType;
    }
}
