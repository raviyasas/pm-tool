package com.app.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum IssueState {

    OPEN("open"),
    IN_PROGRESS("in_progress"),
    TESTING("testing"),
    DEPLOY("deploy");

    private String issueState;

    private IssueState(String issueState) {
        this.issueState = issueState;
    }


    @JsonCreator
    public static IssueState decode(final String code) {
        return Stream.of(IssueState.values()).filter(targetEnum -> targetEnum.issueState.equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getCode() {
        return issueState;
    }
}
