package com.app.util;

public enum CommonMessage {
    SUCCESS(0, "Success"),
    FAIL(1, "Fail"),
    NO_CONTENT(2, "No Content"),
    CREATED(3, "Created"),
    UPDATED(4, "Updated"),
    BAD_REQUEST(5, "Bad Request"),
    EXCEPTION(6, "Exception"),
    NOT_FOUND(7, "Not Found"),
    CONFLICT(8, "Conflict");

    private final int value;
    private String message;

    CommonMessage(int value) {
        this.value = value;
    }

    CommonMessage(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int value() {
        return value;
    }

    public String message() {
        return message;
    }
}
