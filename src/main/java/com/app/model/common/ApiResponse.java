package com.app.model.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

@JsonPropertyOrder({ "httpHeaders", "httpStatusCode", "apiStatusCode", "message", "data", "additionalParams" })
public class ApiResponse<T> {

    private final HttpHeaders httpHeaders;
    private final int httpStatusCode;
    private final String message;
    private final T data;
    private final int apiStatusCode;
    private final Map<String, Object> additionalParams;

    private ApiResponse(ApiResponseBuilder builder) {
        this.httpHeaders = builder.httpHeaders;
        this.httpStatusCode = builder.httpStatusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
        this.apiStatusCode = builder.apiStatusCode;
        this.additionalParams = builder.additionalParams;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public int getApiStatusCode() {
        return apiStatusCode;
    }

    public Map<String, Object> getAdditionalParams() {
        return additionalParams;
    }

    public static class ApiResponseBuilder<T> {

        private HttpHeaders httpHeaders = new HttpHeaders();
        private final int httpStatusCode;
        private final int apiStatusCode;
        private final String message;
        private T data;
        private Map<String, Object> additionalParams = Collections.emptyMap(); // set empty map optional parameter

        public ApiResponseBuilder(int httpStatusCode, int apiStatusCode, String message) {
            this.httpStatusCode = httpStatusCode;
            this.apiStatusCode = apiStatusCode;
            this.message = message;
        }

        public ApiResponseBuilder<T> withHttpHeader(HttpHeaders httpHeader) {
            this.httpHeaders = httpHeader;
            return this;
        }

        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> withAdditionalParams(Map<String, Object> additionalParams) {
            this.additionalParams = additionalParams;
            return this;
        }

        public ResponseEntity<ApiResponse> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return ResponseEntity.status(apiResponse.getHttpStatusCode()).headers(apiResponse.getHttpHeaders())
                    .body(apiResponse);
        }
    }
}

