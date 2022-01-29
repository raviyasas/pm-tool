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
    CONFLICT(8, "Conflict"),
    BAD_GATEWAY(9, "Bad Gateway"),
    UNAUTHORIZED(10, "Unauthorized"),
    AUTH_HEADER(11,"Authorization"),
    BEARER(12, "Bearer "),
    SECRET_KEY(13,"q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh");

    private final int value;
    private String message;

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
